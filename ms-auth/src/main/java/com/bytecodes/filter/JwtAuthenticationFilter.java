package com.bytecodes.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Si ya estamos autenticados (Service Token) ignoramos este filtro
        if(Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(Objects.isNull(authHeader) || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(13);

            Jwt jwt = jwtDecoder.decode(token);

            UserDetails user = userDetailsService.loadUserByUsername(jwt.getSubject());

            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            userToken.setDetails(user);
            SecurityContextHolder.getContext().setAuthentication(userToken);

            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.warn("Hubo algún problema al autenticar");
            filterChain.doFilter(request, response);
        }

    }
}
