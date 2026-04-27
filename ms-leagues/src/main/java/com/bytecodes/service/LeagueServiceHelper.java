package com.bytecodes.service;

import com.bytecodes.dto.external.Season;
import com.bytecodes.dto.response.SeasonResponseDTO;

import java.util.List;

public interface LeagueServiceHelper {

    Integer findCurrentSeasonYear(List<Season> seasons);
    String findCurrentSeasonStart(List<Season> seasons);
    String findCurrentSeasonEnd(List<Season> seasons);
    SeasonResponseDTO toCurrentSeason(List<Season> seasons);
    List<Integer> getLastSeasons(List<Season> seasons);

}
