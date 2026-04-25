package com.bytecodes.dto.external;

public record Season(
        Integer year,
        String start,
        String end,
        boolean current,
        Coverage coverage
) {}
