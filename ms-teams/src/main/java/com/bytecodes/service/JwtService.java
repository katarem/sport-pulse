package com.bytecodes.service;

import com.bytecodes.config.JwtProperties;
import com.bytecodes.dto.external.ValidationResponseDTO;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(JwtProperties.class)
public interface JwtService {

    ValidationResponseDTO validateJwt(String token);

}
