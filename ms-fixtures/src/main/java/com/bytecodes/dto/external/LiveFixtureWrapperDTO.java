package com.bytecodes.dto.external;

import lombok.Data;

import java.util.Map;

@Data
public class LiveFixtureWrapperDTO {
    private FixtureDTO fixture;
    private LeagueDTO league;
    private FixtureStatusDTO status;
    private Map<String, TeamDTO> teams;
    private Map<String, Integer> goals;
}
