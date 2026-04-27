package com.bytecodes.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor

// responde al endpoint GET/api/standings
public class StandingResponseDTO {
    private LeagueDTO league;
    private List<StadingDTO> stadings;


}
