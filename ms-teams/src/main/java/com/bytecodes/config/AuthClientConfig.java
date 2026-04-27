package com.bytecodes.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthClientConfig {

    @Bean
    RequestInterceptor requestInterceptor() {
        return request -> request.header("X-SPORTS-PULSE-API-TOKEN", "MS-TEAMS");
    }


}
