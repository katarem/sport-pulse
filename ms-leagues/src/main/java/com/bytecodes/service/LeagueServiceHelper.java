package com.bytecodes.service;

import com.bytecodes.dto.external.SeasonDTO;
import com.bytecodes.dto.response.SeasonResponseDTO;

import java.util.List;

public interface LeagueServiceHelper {

    Integer findCurrentSeasonYear(List<SeasonDTO> seasons);
    String findCurrentSeasonStart(List<SeasonDTO> seasons);
    String findCurrentSeasonEnd(List<SeasonDTO> seasons);
    SeasonResponseDTO toCurrentSeason(List<SeasonDTO> seasons);
    List<Integer> getLastSeasons(List<SeasonDTO> seasons);

}
