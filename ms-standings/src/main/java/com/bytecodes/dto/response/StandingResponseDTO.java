package com.bytecodes.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString


public class StandingResponseDTO {
    private LeagueDTO league;
    private List<StandingDTO> standings;


}
