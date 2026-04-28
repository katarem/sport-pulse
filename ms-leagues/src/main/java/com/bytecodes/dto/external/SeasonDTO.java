package com.bytecodes.dto.external;

public record SeasonDTO(
        Integer year,
        String start,
        String end,
        boolean current
) {}
