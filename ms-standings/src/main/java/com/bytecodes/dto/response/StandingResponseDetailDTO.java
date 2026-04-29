package com.bytecodes.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString

// responde al endpoint GET/api/standing/team/{teamid}
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
