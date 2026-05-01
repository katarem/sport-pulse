package com.bytecodes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSubscriptionException.class)
    ResponseEntity<ErrorResponse> handleSubscriptionBusinessException(InvalidSubscriptionException ex) {
        var error = new ErrorResponse("BUSINESS_VALIDATION_ERROR", ex.getMessage(), Instant.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(SubscriptionAlreadyExistsException.class)
    ResponseEntity<ErrorResponse> handleSubscriptionAlreadyExistsException(SubscriptionAlreadyExistsException ex) {
        var error = new ErrorResponse("SUBSCRIPTION_ALREADY_EXISTS", ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    ResponseEntity<ErrorResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        var error = new ErrorResponse("SUBSCRIPTION_NOT_FOUND", ex.getMessage(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> handleFieldError(MethodArgumentNotValidException ex) {
        var errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        var error = new ErrorResponse("FIELDS_ERROR", errorMessage, Instant.now());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(InvalidAccessException.class)
    ResponseEntity<Void> handleAccessInvalidException() {
        log.warn("Acceso inválido");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
