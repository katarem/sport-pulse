package com.bytecodes.business;

import java.time.Instant;

public record RateLimitError(
        String error,
        String message,
        long retryAfter,
        Instant timestamp
) {
}
