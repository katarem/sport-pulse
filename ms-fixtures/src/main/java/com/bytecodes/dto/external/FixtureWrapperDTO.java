package com.bytecodes.dto.external;

import lombok.Data;

import java.util.Map;

@Data
public class FixtureWrapperDTO {
    private FixtureDTO fixture;
    private LeagueDTO league;
    private Map<String, TeamDTO> teams;
    private Map<String, Integer> goals;
}
