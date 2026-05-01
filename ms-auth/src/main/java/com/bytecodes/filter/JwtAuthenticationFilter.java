package com.bytecodes.filter;

import com.bytecodes.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        // Si ya estamos autenticados (Service Token) ignoramos este filtro
        if(Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
            log.info("Ya había autenticación anterior");
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(Objects.isNull(authHeader) || authHeader.isBlank()) {
            log.info("Entrando al endpoint sin autenticación");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.replace("Bearer ", "");

            Jwt jwt = jwtService.readToken(token);

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
