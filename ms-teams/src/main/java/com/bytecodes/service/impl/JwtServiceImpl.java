package com.bytecodes.service.impl;

import com.bytecodes.client.AuthClient;
import com.bytecodes.dto.external.ValidationResponseDTO;
import com.bytecodes.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final AuthClient client;

    @Override
    public ValidationResponseDTO validateJwt(String token) {
        return client.validateToken(token);
    }
}
