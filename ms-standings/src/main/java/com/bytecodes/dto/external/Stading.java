package com.bytecodes.dto.external;

import com.bytecodes.dto.response.TeamDto;

public record Stading (
        Integer rank,
        TeamDto team,
        Integer points,
        Integer goalsDiff,
        String group,
        String form,
        String status,
        String description,
        AllStats all,
        HomeStats home,
        AwayStats away,
        String update
) {
}
