package com.bytecodes.config;

import com.bytecodes.filter.JwtAuthenticationFilter;
import com.bytecodes.filter.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;

@Configuration
@EnableMethodSecurity()
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter,TokenAuthenticationFilter tokenAuthenticationFilter) throws Exception {

        http
                .sessionManagement(ses ->
                        ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
               // .addFilterBefore(tokenAuthenticationFilter, JwtAuthenticationFilter.class);

        return http.build();
    }


}
