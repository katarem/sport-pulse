package com.bytecodes.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString

// responde al endpoint GET/api/standings
public class StandingResponseDTO {
    private LeagueDTO league;
    private List<StandingDTO> stadings;


}
