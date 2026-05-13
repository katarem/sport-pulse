package com.bytecodes.service;

import com.bytecodes.config.JwtProperties;
import com.bytecodes.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
/* CAMBIO DE LIBRERIA
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

 */
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class JwtService {
/*
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

 */
private final JwtProperties jwtProperties;

    public SignedJWT generateToken(UserEntity user) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .issuer("MS-AUTH")
                    .subject(user.getUsername())
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plusMillis(jwtProperties.getExpiration())))
                    .build();

            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256),
                    claims
            );

            JWSSigner signer = new MACSigner(jwtProperties.getSecretKey().getBytes());
            signedJWT.sign(signer);

            return signedJWT;

        } catch (Exception e) {
            throw new RuntimeException("Error generando token", e);
        }
    }

    public SignedJWT readToken(String token) {
        try {
            SignedJWT jwt = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(jwtProperties.getSecretKey().getBytes());

            if (!jwt.verify(verifier)) {
                throw new RuntimeException("Token inválido");
            }

            Date expiration = jwt.getJWTClaimsSet().getExpirationTime();
            if (expiration.before(new Date())) {
                throw new RuntimeException("Token expirado");
            }

            return jwt;

        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado", e);
        }
    }

}
