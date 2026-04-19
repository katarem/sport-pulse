package com.bytecodes.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LeagueDetailResponseDTO {
    private int id;
    private String name;
    private String type;
    private String country;
    private String logo;
    private List<Integer> seasons;
    private SeasonResponseDTO currentSeason;
}
