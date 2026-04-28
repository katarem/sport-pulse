package com.bytecodes.service;

import com.bytecodes.config.JwtProperties;
import com.bytecodes.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class JwtService {

    private final JwtProperties jwtProperties;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public Jwt generateToken(UserEntity user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("MS-AUTH")
                .issuedAt(Instant.now())
                .subject(user.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtProperties.getExpiration()))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
    }

    public Jwt readToken(String token) {
        return jwtDecoder.decode(token);
    }

}
