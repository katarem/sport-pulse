package com.bytecodes.controller;

import com.bytecodes.entity.CreateUser;
import com.bytecodes.response.ErrorResponse;
import com.bytecodes.response.ValidationErrorResponse;
import com.bytecodes.response.ValidationResponse;
import com.bytecodes.model.ApiUser;
import com.bytecodes.model.User;
import com.bytecodes.model.UserLoginParameters;
import com.bytecodes.model.UserToken;
import com.bytecodes.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Endpoint para registrarse como usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Registro correcto", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = User.class), examples = {
            @ExampleObject(value = """
                    { "id": "550e8400-e29b-41d4-a716-446655440000", "username": "javier_ruiz", "email": "javier@email.com", "role": "USER", "createdAt": "2025-01-15T10:30:00Z"}
                        """)})
        }),
        @ApiResponse(responseCode = "409", description = "Conflicto en registro", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = {
            @ExampleObject(value = """
                        { "error": "USER_ALREADY_EXISTS", "message": "Ya existe un usuario con ese email", "timestamp": "2025-01-15T10:30:00Z"}
                        """)})
        })
    })
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody CreateUser user) {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }
    @Operation(summary = "Endpoint para iniciar sesión como usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inicio de sesión correcto", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = UserToken.class), examples = {
            @ExampleObject(value = """
                    { "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNUy1BVVRIIiwic3ViIjoiNjYwMDJiZTktOTM3OS00Y2E0LTk0ZWUtNWE3NDE4NjQ3ODU5IiwiZXhwIjoxNzc3NjY1OTQwLCJpYXQiOjE3Nzc2NjIzNDB9.xMs-4noYqNNsvrzfRv6g8hRd9jooZEaa5onb7N7NsDk", "tokenType": "Bearer", "expiresIn": 3600, "userId": "550e8400-e29b-41d4-a716-446655440000"}
                        """)})
        }),
        @ApiResponse(responseCode = "401", description = "Token inválido", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class), examples = {
            @ExampleObject(value = """
                        { "error": "INVALID_CREDENTIALS", "message": "Email o contraseña incorrectos", "timestamp": "2025-01-15T10:30:00Z"}
                        """)})
        })
    })
    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody @Valid UserLoginParameters loginParameters) {
        return ResponseEntity.ok(authService.login(loginParameters.getEmail(), loginParameters.getPassword()));
    }

    @PostMapping("/validate")
    @Operation(summary = "Endpoint interno para validar token enviado por el usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token válido", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationResponse.class), examples = {
            @ExampleObject(value = "{\"valid\": true, \"userId\":\"550e8400-e29b-41d4-a716-446655440000\", \"username\":\"javier_ruiz\", \"role\": \"USER\"}")})
        }),
        @ApiResponse(responseCode = "401", description = "Token inválido", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class), examples = {
            @ExampleObject(value = "{\"valid\": false, \"error\":\"TOKEN_EXPIRED\", \"message\":\"El token ha expirado\"}")})
        })
    })
    @SecurityRequirements(value = {@SecurityRequirement(name = "Internal Api Key"), @SecurityRequirement(name = "JWT")})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ValidationResponse> validateUser(@AuthenticationPrincipal ApiUser apiUser) {
        return ResponseEntity.ok(authService.validateUser(apiUser.getUserToken()));
    }

}
