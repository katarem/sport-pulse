package com.bytecodes.dto.external;

public record League(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season,
        List<List<Standing>> standings
) {
}
