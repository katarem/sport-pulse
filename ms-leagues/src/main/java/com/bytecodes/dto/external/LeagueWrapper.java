package com.bytecodes.dto.external;

import java.util.List;

public record LeagueWrapper(
        League league,
        Country country,
        List<Season> seasons
) {}
