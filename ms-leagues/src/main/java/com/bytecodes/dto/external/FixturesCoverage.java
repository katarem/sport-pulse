package com.bytecodes.dto.external;

public record FixturesCoverage(
        boolean events,
        boolean lineups,
        boolean statistics_fixtures,
        boolean statistics_players
) {}

