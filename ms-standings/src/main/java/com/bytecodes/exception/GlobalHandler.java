package com.bytecodes.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
public class GlobalHandler {

    // Excepcion si el parametro esperado es un Int y se para un String
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidParam(MethodArgumentTypeMismatchException e) {
        assert e.getPropertyName() != null;
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of("FIELDS_ERROR",
                        Map.of(e.getPropertyName(), "El Parametro introducido no es un numero, validelo e intente de nuevo").toString()));
    }

    @ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<ErrorResponse> handleFeignForbidden(FeignException.Forbidden ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.of("TEAM_SERVICE_FORBIDDEN","El servicio ms-teams devolvió 403"));

    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignGeneric(FeignException ex) {
        String message = ex.contentUTF8();

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ErrorResponse.of("TEAM_SERVICE_ERROR",ex.getMessage().split("\"teamId\":\"")[1].split("\"")[0]));
    }


}
