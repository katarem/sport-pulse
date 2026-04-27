package com.bytecodes.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(String code, Map<String, String> errors, String timestamp) {
    public static ErrorResponse of(String code, Map<String, String> errors) {
        return new ErrorResponse(code, errors, Instant.now().toString());
    }
}