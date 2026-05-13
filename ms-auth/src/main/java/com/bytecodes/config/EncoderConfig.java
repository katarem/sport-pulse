package com.bytecodes.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class EncoderConfig {
/*
    @Bean
    JwtEncoder jwtEncoder(JwtProperties jwtProperties) {
        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtProperties.getSecretKey().getBytes()));
    }

    @Bean
    JwtDecoder jwtDecoder(JwtProperties jwtProperties) {
        byte[] bytes = jwtProperties.getSecretKey().getBytes();
        SecretKeySpec originalKey = new SecretKeySpec(bytes, 0, bytes.length, MacAlgorithm.HS256.getName());
        return NimbusJwtDecoder.withSecretKey(originalKey).macAlgorithm(MacAlgorithm.HS256).build();
    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
