package com.bytecodes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(LeagueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLeagueNotFoundException(LeagueNotFoundException e) {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.of("LEAGUE_NOT_FOUND",
                        Map.of("teamId", e.getMessage()))
                );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("FIELDS_ERROR",
                        Map.of(e.getPropertyName(), "El Parametro introducido no es un numero, validelo e intente de nuevo")));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentNotValidException e) {
        Function<ObjectError, String> argumentErrorKeyResolver = err -> {
            if (err.getCodes() == null)
                return err.getCode();
            var longCode = err.getCodes()[0].split("\\.");
            return longCode[longCode.length - 1];
        };

        Function<ObjectError, String> argumentErrorValueResolver = err -> {
            if(Objects.isNull(err.getDefaultMessage()))
                return "Unknown error";
            return err.getDefaultMessage();
        };

        var errorsMessage = e.getAllErrors()
                .stream().collect(Collectors.groupingBy(
                        argumentErrorKeyResolver,
                        Collectors.mapping(argumentErrorValueResolver, Collectors.toList())));

        return ResponseEntity.badRequest().body(ErrorResponse.of("FIELDS_ERROR", errorsMessage));
    }
}
