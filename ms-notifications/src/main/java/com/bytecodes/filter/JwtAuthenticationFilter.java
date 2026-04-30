package com.bytecodes.filter;

import com.bytecodes.dto.response.external.ValidationResponseDTO;
import com.bytecodes.model.ApiUser;
import com.bytecodes.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

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
            String token = authHeader.replace("Bearer ", "");
            log.info("autenticando...");
            log.debug("token: {}", token);
            ValidationResponseDTO validationResponseDTO = jwtService.validateJwt(token);
            log.info("obtenido autenticación del endpoint");
            if(!validationResponseDTO.isValid()) {
                filterChain.doFilter(request, response);
                return;
            }

            GrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + validationResponseDTO.getRole());

            ApiUser apiUser = new ApiUser(validationResponseDTO.getUsername(), validationResponseDTO.getUserId());

            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(apiUser, null, Collections.singletonList(role));

            SecurityContextHolder.getContext().setAuthentication(userToken);

        } catch (Exception exception) {
            log.debug("Hubo algún problema al autenticar: {}", Optional.ofNullable(exception.getMessage())
                    .orElse("Unknown Error"));
        }
        
        filterChain.doFilter(request, response);

    }
}
