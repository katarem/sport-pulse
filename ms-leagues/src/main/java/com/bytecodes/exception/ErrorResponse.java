package com.bytecodes.exception;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(String error, String message, Instant timestamp)
{
    public static ErrorResponse of(String error, String message) {
        return new ErrorResponse(error, message, Instant.now());
    }
}
