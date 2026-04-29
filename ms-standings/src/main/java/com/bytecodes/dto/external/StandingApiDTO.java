package com.bytecodes.dto.external;

import com.bytecodes.dto.response.TeamDto;

public record StandingApiDTO(
        Integer rank,
        TeamDto team,
        Integer points,
        Integer goalsDiff,
        String group,
        String form,
        String status,
        String description,
        AllStatsDTO all,
        HomeStats home,
        AwayStats away,
        String update
) {
}
