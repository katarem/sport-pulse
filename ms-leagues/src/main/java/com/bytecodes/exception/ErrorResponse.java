package com.bytecodes.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(String code, Map<String, ?> errors, Instant timestamp)
{
    public static ErrorResponse of(String code, Map<String, ?> errors) {
        return new ErrorResponse(code, errors, Instant.now());
    }
}
