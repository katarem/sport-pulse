package com.bytecodes.filter;

import com.bytecodes.config.TokenProperties;
import com.bytecodes.model.ApiUser;
import com.bytecodes.service.AuthService;
import com.bytecodes.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(TokenProperties.class)
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(tokenProperties.getAllowedServices().isEmpty()) {
            log.warn("Ningún servicio está permitido");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("Comprobando si la api key ha sido mandada");
        String token = request.getHeader("X-SPORTS-PULSE-API-TOKEN");

        if(Objects.isNull(token) || token.isBlank()) {
            log.warn("La api key que ha mandado no se reconoce");
            filterChain.doFilter(request, response);
            return;
        }

        if(!tokenProperties.getAllowedServices().contains(token)) {
            log.warn("La api key que ha mandado no se reconoce");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        

        log.info("La api key está reconocida, autenticando...");

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        
        String userToken = "";

        if(Objects.nonNull(authHeader) && !authHeader.isBlank()) {
            userToken = authHeader.replace("Bearer ", "");
        }
        
        ApiUser apiUser = new ApiUser(token, userToken);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(apiUser, null, Collections.singletonList(grantedAuthority));

        

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("Autenticado correctamente, entrando al endpoint");
        filterChain.doFilter(request, response);

    }
}
