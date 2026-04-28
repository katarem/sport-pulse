package com.bytecodes.service;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.UserEntity;
import com.bytecodes.exception.UserTokenExpiredException;
import com.bytecodes.response.ValidationResponse;
import com.bytecodes.exception.InvalidCredentialsException;
import com.bytecodes.mapper.UserMapper;
import com.bytecodes.model.User;
import com.bytecodes.model.UserRole;
import com.bytecodes.model.UserToken;
import com.bytecodes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity obtained = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario %s no encontrado", username)));

        return userMapper.toSecurityUser(obtained);
    }

    @Override
    public UserToken login(String email, String password) {
        log.info("AuthService > login");
        log.debug("Autenticando email={}", email);
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);
        log.debug("usuario encontrado");
        if(!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Contraseña incorrecta");
            log.info("AuthService < login");
            throw new InvalidCredentialsException();
        }
        log.debug("generando token");
        Jwt jwt = jwtService.generateToken(user);
        log.debug("token generado");
        Duration expirationTime = Duration.between(Instant.now(),jwt.getExpiresAt());
        log.info("Autenticación correcta");
        log.info("AuthService < login");
        return UserToken.builder()
                .token(jwt.getTokenValue())
                .tokenType("Bearer")
                .userId(user.getId())
                .expiresIn(expirationTime.toMillis())
                .build();
    }

    @Override
    public ValidationResponse validateUser(String token) {
        try {
            log.info("AuthtenticationService > validateUser");
            Jwt jwt = jwtService.readToken(token);
            UserEntity user = userRepository.findByUsername(jwt.getSubject())
                    .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario %s no encontrado", jwt.getSubject())));

            log.info("AuthenticationService < validateUser");
            return userMapper.toValidationUser(user);
        } catch (JwtException e) {
            log.warn("Token expirado");
            throw new UserTokenExpiredException();
        }
    }

    @Override
    public User register(CreateUser user) {
        log.info("AuthService > register");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity entity = userMapper.toEntity(user);
        entity.setCreatedAt(Instant.now());
        entity.setRole(UserRole.USER);

        entity = userRepository.save(entity);

        log.info("AuthService < register");
        return userMapper.toModel(entity);
    }

}
