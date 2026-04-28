package com.bytecodes.service.impl;

import com.bytecodes.client.AuthClient;
import com.bytecodes.dto.external.ValidationResponseDTO;
import com.bytecodes.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final AuthClient client;

    @Override
    public ValidationResponseDTO validateJwt(String token) {
        try {
            return client.validateToken(token);
        } catch (Exception e) {
            log.error("error {}", e.getMessage());
            return null;
        }
    }
}
