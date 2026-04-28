package com.bytecodes.service;

import com.bytecodes.dto.external.ValidationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {

    ValidationResponseDTO validateJwt(String token);

}
