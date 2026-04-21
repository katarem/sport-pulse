package com.bytecodes.dto.external;

public record VenueDTO(
        int id,
        String name,
        String address,
        String city,
        int capacity,
        String surface,
        String image
) {
}
