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
public class StandingResponseDTO {
    private LeagueDTO league;
    private List<StadingsDTO> stadings;


}
