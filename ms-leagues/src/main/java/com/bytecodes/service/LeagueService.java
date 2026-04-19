package com.bytecodes.service;

import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;

import java.util.List;

public interface LeagueService {

    public List<LeagueResponseDTO> getLeagues(String country, Integer season);
    public LeagueDetailResponseDTO getLeagueById(int leagueId);
}
