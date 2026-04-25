package com.bytecodes.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StandingProperties.class)
public class StandingConfig {
    private final StandingProperties properties;
    public StandingConfig(StandingProperties properties) {
        this.properties = properties;
    }


    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("x-apisports-key",properties.getKey());
        };
    }
}
