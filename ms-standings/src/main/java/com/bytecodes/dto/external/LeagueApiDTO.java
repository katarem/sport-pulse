package com.bytecodes.dto.external;

import java.util.List;

public record LeagueApiDTO(
        Integer id,
        String name,
        String country,
        String logo,
        String flag,
        Integer season,
        List<List<StandingApiDTO>> standings
) {
}
