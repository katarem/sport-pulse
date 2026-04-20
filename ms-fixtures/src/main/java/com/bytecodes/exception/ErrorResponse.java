package com.bytecodes.exception;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse<T> {
    private String code;
    private T errors;
    private Instant timestamp;
}
