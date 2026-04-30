package com.bytecodes.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> argumentError(MethodArgumentNotValidException error) {
        var errorMessage = error.getAllErrors().get(0).getDefaultMessage();
        var response = new ErrorResponse("FIELDS_ERROR", errorMessage, Instant.now());
        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler({ExternalApiException.class, FeignException.FeignServerException.class})
    ResponseEntity<ErrorResponse> externalApiError(ExternalApiException exception) {
        var error = new ErrorResponse("EXTERNAL_API_ERROR", exception.getMessage(), Instant.now());
        return ResponseEntity.badRequest()
                .body(error);
    }

    @ExceptionHandler(UnknownFormatException.class)
    ResponseEntity<ErrorResponse> unknownErrorFormatError(UnknownFormatException exception) {
        var error = new ErrorResponse("INTERNAL_SERVER_ERROR", exception.getMessage(), Instant.now());
        return ResponseEntity.badRequest()
                .body(error);
    }


}
