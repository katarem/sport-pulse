package com.bytecodes.config;

import com.bytecodes.business.RateLimitError;
import com.bytecodes.utils.RateLimitUtils;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(RateLimitProperties.class)
public class RateLimitConfig {

    private final RateLimitProperties properties;

    public RateLimitConfig(RateLimitProperties properties) {
        this.properties = properties;
    }
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Bean
    public HandlerFilterFunction<ServerResponse, ServerResponse> globalRateLimitFilter() {
        return (request, next) -> {
            String clientIp = RateLimitUtils.obtainClientIp(request);
            Bucket bucket = buckets.computeIfAbsent(clientIp, key -> newBucket());

            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

            if (probe.isConsumed()) {
                return next.handle(request);
            }

            long retryAfterSeconds = Math.max(
                    1,
                    TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill())
            );

            RateLimitError error = new RateLimitError("RATE_LIMIT_EXCEEDED", "Demasiadas peticiones. Límite 60 req/min", retryAfterSeconds, Instant.now());

            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(error);
        };
    }

    private Bucket newBucket() {
        Bandwidth limit = Bandwidth.classic(
                properties.getCapacity(),
                Refill.intervally(properties.getRefillAmount(), Duration.of(properties.getTime(), properties.getTimeUnit().toChronoUnit()))
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }


}
