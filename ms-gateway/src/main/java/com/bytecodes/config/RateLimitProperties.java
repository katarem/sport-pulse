package com.bytecodes.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "ratelimit")
@Validated
public class RateLimitProperties {

    @Min(1L)
    private long capacity;

    @Min(1L)
    private long refillAmount;

    @Min(1L)
    private long time;

    @NotNull
    private TimeUnit timeUnit;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public long getRefillAmount() {
        return refillAmount;
    }

    public void setRefillAmount(long refillAmount) {
        this.refillAmount = refillAmount;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
