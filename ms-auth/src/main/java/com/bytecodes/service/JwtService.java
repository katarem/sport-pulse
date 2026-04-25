package com.bytecodes.service;

import com.bytecodes.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    public static String generateToken(UserDetails user) {

    }


    public static String decodeToken(String token) {
    }


}
