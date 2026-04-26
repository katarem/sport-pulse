package com.bytecodes.response;

import java.time.Instant;

public record ValidationErrorResponse(
    Boolean valid,
    String error,
    String message,
    Instant timestamp
) {
}
