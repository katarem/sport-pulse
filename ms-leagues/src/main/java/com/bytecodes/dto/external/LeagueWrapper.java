package com.bytecodes.dto.external;

import java.util.List;

public record LeagueWrapper(
        LeagueDTO league,
        CountryDTO country,
        List<SeasonDTO> seasons
) {}
