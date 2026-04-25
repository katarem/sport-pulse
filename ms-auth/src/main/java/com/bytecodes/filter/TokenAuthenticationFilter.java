package com.bytecodes.filter;

import com.bytecodes.config.TokenProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(tokenProperties.getAllowedServices().isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("X-SPORTS-PULSE-API-TOKEN");

        if(!tokenProperties.getAllowedServices().contains(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_INTERNAL_SERVICE");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(token, null, Collections.singletonList(grantedAuthority));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
