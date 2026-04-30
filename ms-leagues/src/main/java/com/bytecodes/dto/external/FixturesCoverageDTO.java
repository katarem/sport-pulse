package com.bytecodes.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FixturesCoverageDTO(
        boolean events,
        boolean lineups,
        @JsonProperty("statistics_fixtures")
        boolean statisticsFixtures,
        @JsonProperty("statistics_players")
        boolean statisticsPlayers
) {}

