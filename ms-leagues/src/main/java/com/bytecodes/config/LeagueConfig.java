package com.bytecodes.config;

import feign.RequestInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LeagueConfig {

    @Value("${api.football.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            template.header("x-apisports-key", apiKey);
        };
    }
}
