package com.bytecodes.controller;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.entity.ValidationUser;
import com.bytecodes.model.User;
import com.bytecodes.model.UserLoginParameters;
import com.bytecodes.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody CreateUser user) {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginParameters loginParameters) {
        return ResponseEntity.ok(authService.login(loginParameters.getEmail(), loginParameters.getPassword()));
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationUser> validateUser(@RequestHeader("Authorization") String authToken) {
        String token = authToken.substring(13);
        return ResponseEntity.ok(authService.validateUser(token));
    }

}
