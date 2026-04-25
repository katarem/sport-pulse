package com.bytecodes.dto.external;

import java.util.List;
import java.util.Map;

public record ApiLeagueResponse(
        List<LeagueWrapper> response
) {}

