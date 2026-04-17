package com.bytecodes.service;

import com.bytecodes.dto.response.LeagueDetailResponse;
import com.bytecodes.dto.response.LeagueResponse;

import java.util.List;

public interface LeagueService {

    public List<LeagueResponse> getLeagues(String country, Integer season);
    public LeagueDetailResponse getLeagueById(int leagueId);
}
