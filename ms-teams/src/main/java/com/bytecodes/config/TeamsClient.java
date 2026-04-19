package com.bytecodes.config;

import feign.RequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(TeamsProperties.class)
public class TeamsClient {
    private final TeamsProperties properties;
    public TeamsClient(TeamsProperties properties) {
        this.properties = properties;
    }


    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("x-apisports-key",properties.getKey());
        };
    }
}
