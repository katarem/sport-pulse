package com.bytecodes.config;

import com.bytecodes.client.ClientErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LeagueClientProperties.class)
public class FixtureClientConfig {

    @Bean
    RequestInterceptor authMiddleware(LeagueClientProperties properties) {
        return (request) ->
                request.header("x-apisports-key", properties.getApiKey());
    }

    @Bean
    ErrorDecoder errorDecoder() {
        return new ClientErrorDecoder();
    }

}