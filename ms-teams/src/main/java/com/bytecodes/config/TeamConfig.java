package com.bytecodes.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TeamProperties.class)
public class TeamConfig {
    private final TeamProperties properties;
    public TeamConfig(TeamProperties properties) {
        this.properties = properties;
    }


    @Bean
    public RequestInterceptor teamRequestInterceptor() {
        return template ->
                template.header("x-apisports-key",properties.getKey());
    }
}
