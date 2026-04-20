package com.bytecodes.config;

import feign.RequestInterceptor;
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

}