package com.bytecodes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(LeagueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLeagueNotFoundException(LeagueNotFoundException e) {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("LEAGUE_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("FIELDS_ERROR","El Parametro introducido no es un numero, validelo e intente de nuevo"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentNotValidException e) {

        return ResponseEntity.badRequest().body(ErrorResponse.of("FIELDS_ERROR", e.getAllErrors().get(0).getDefaultMessage()));
    }
}
