package com.bytecodes.service;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.UserEntity;
import com.bytecodes.entity.ValidationUser;
import com.bytecodes.exception.InvalidCredentialsException;
import com.bytecodes.mapper.UserMapper;
import com.bytecodes.model.User;
import com.bytecodes.model.UserToken;
import com.bytecodes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
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

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Jwt jwt = jwtService.generateToken(user);

        Duration expirationTime = Duration.between(Instant.now(),jwt.getExpiresAt());

        return UserToken.builder()
                .token(jwt.getTokenValue())
                .tokenType("Bearer")
                .userId(user.getId())
                .expiresIn(expirationTime.toMillis())
                .build();
    }

    @Override
    public ValidationUser validateUser(String token) {

        Jwt jwt = jwtService.readToken(token);

        UserEntity user = userRepository.findByUsername(jwt.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario %s no encontrado", jwt.getSubject())));

        return userMapper.toValidationUser(user);
    }

    @Override
    public User register(CreateUser user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserEntity entity = userMapper.toEntity(user);
        entity.setActive(true);
        entity.setCreatedAt(Instant.now());

        entity = userRepository.save(entity);

        return userMapper.toModel(entity);
    }

}
