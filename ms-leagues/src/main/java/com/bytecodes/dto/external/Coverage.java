package com.bytecodes.dto.external;

public record Coverage(
        FixturesCoverage fixtures,
        boolean standings,
        boolean players,
        boolean top_scorers,
        boolean top_assists,
        boolean top_cards,
        boolean injuries,
        boolean predictions,
        boolean odds
) {}
