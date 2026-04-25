package com.bytecodes.dto.external;

public record TeamDTO(
        int id,
        String name,
        String  code,
        String country,
        int founded,
        Boolean national,
        String logo) {
}
