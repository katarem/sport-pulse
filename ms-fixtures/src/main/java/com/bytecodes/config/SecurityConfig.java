package com.bytecodes.config;

import com.bytecodes.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter
    ) throws Exception {

        http
                .sessionManagement(ses ->
                        ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/actuator/health", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, e) ->
                        res.sendError(401, "Unauthorized"))
                    .accessDeniedHandler((req, res, e) ->
                        res.sendError(403, "Forbidden"))
        		)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
