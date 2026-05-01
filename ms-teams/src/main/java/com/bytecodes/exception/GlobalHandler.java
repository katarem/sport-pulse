package com.bytecodes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalHandler {


// Exception requerida en el proyecto.
    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLeagueNotFoundException(TeamNotFoundException e) {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("TEAM_NOT_FOUND", e.getMessage()));
    }
    // Exception del @SpringQueryMap
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentNotValidException e) {
       
        var errorMessage = e.getAllErrors()
                .get(0).getDefaultMessage();

        return ResponseEntity.badRequest().body(ErrorResponse.of("FIELDS_ERROR", errorMessage));
    }

    // Excepcion si el parametro esperado es un Int y se para un String
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("FIELDS_ERROR", "El Parametro introducido no es un numero, validelo e intente de nuevo"));
    }
}
