package com.bytecodes.service.impl;

import com.bytecodes.client.LeagueFeing;
import com.bytecodes.dto.external.ApiLeagueResponse;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.response.LeagueDetailResponse;
import com.bytecodes.dto.response.LeagueResponse;
import com.bytecodes.exception.LeagueNotFoundException;
import com.bytecodes.mapper.LeagueMapper;
import com.bytecodes.service.LeagueService;
import com.bytecodes.service.LeagueServiceHelper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueFeing client;
    private final LeagueMapper mapper;
    private final LeagueServiceHelper helper;

    public LeagueServiceImpl(LeagueFeing client, LeagueMapper leagueMapper, LeagueServiceHelper helper) {
        this.client = client;
        this.mapper = leagueMapper;
        this.helper = helper;
    }

    @Override
    public List<LeagueResponse> getLeagues(String country, Integer season, Integer id) {
        return client.getLeagues(country, season, id).response().stream()
                .map(wrapper -> {
                    LeagueResponse dto = mapper.toLeagueResponse(wrapper);

                    dto.setCurrentSeason(helper.findCurrentSeasonYear(wrapper.seasons()));
                    dto.setStartDate(helper.findCurrentSeasonStart(wrapper.seasons()));
                    dto.setEndDate(helper.findCurrentSeasonEnd(wrapper.seasons()));

                    return dto;
                })
                .toList();
    }

    @Override
    public LeagueDetailResponse getLeagueById(int leagueId) {
        ApiLeagueResponse apiResponse = client.getLeagueById(leagueId);

        LeagueWrapper wrapper = apiResponse.response().stream()
                .findFirst()
                .orElseThrow(() -> new LeagueNotFoundException("No existe una liga con el ID proporcionado"));

        LeagueDetailResponse dto = mapper.toLeagueDetailResponse(wrapper);

        dto.setSeasons(helper.getLastSeasons(wrapper.seasons()));
        dto.setCurrentSeason(helper.toCurrentSeason(wrapper.seasons()));

        return dto;
    }
}
