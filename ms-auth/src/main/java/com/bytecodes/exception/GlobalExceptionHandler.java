package com.bytecodes.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException e) {

        String exceptionMessage = e.getMostSpecificCause().getMessage();
        String errorMessage = "Conflicto desconocido";
        if(exceptionMessage.contains("users_username_key"))
            errorMessage = "Ya existe un usuario con ese username";
        else if(exceptionMessage.contains("users_email_key"))
            errorMessage = "Ya existe un usuario con ese email";

        ErrorResponse errorResponse = new ErrorResponse("USER_ALREADY_EXISTS", errorMessage, Instant.now());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {

        ErrorResponse errorResponse = new ErrorResponse("FIELDS_ERROR", e.getFieldError().getDefaultMessage(), Instant.now());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse("INVALID_CREDENTIALS", e.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }


}
