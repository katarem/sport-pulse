package com.bytecodes.dto.external;

import java.util.List;

public record ApiLeagueResponseDTO(
        List<LeagueWrapper> response
) {}

