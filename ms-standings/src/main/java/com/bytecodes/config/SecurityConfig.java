package com.bytecodes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(ses ->
                        ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                    .requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/v3/swagger-ui.html", "v3/swagger-ui/**", "/actuator/health").permitAll()
                    .anyRequest().authenticated())
                .exceptionHandling(ex -> ex
                    .authenticationEntryPoint((req, res, e) ->
                        res.sendError(401, "Unauthorized"))
                    .accessDeniedHandler((req, res, e) ->
                        res.sendError(403, "Forbidden"))
        		);

        return http.build();
    }


}
