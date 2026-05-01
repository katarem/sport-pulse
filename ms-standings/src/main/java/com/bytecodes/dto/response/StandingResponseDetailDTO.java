package com.bytecodes.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class StandingResponseDetailDTO {
    private TeamDto team;
    private LeagueDTO league;
    private Integer season;
    private Integer rank;
    private Integer points;
    private Integer played;
    private String form;
    private String description;

}
