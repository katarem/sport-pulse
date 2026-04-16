package com.bytecodes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(LeagueNotFoundException.class)
    public ResponseEntity<?> handleLeagueNotFoundException(LeagueNotFoundException e) {
        return  ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error" , "LEAGUE_NOT_FOUND",
                        "message" , e.getMessage(),
                        "timestamp" , Instant.now().toString()

                ));

    }
}
