package com.bytecodes.config;

import feign.Logger;
import feign.RequestInterceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LeagueProperties.class)
public class LeagueConfig {
    private final LeagueProperties properties;
    public LeagueConfig(LeagueProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template ->
                template.header("x-apisports-key", properties.getKey());
    }
}
