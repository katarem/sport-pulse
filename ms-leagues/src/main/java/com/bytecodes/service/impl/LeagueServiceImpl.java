package com.bytecodes.service.impl;

import com.bytecodes.client.LeagueClient;
import com.bytecodes.config.CacheConfig;
import com.bytecodes.dto.external.ApiLeagueResponse;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.exception.LeagueNotFoundException;
import com.bytecodes.mapper.LeagueMapper;
import com.bytecodes.service.LeagueService;
import com.bytecodes.service.LeagueServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueClient client;
    private final LeagueMapper mapper;
    private final LeagueServiceHelper helper;

    public LeagueServiceImpl(LeagueClient client, LeagueMapper leagueMapper, LeagueServiceHelper helper) {
        this.client = client;
        this.mapper = leagueMapper;
        this.helper = helper;
    }
    @Cacheable(value = "leagues" , unless = "#result == null")
    @Override
    public List<LeagueResponseDTO> getLeagues(String country, Integer season) {
        log.info("Cache MISS Leagues → llamando a API-Football");
        return client.getLeagues(country, season).response().stream()
                .map(wrapper -> {
                    LeagueResponseDTO dto = mapper.toLeagueResponse(wrapper);

                    dto.setCurrentSeason(helper.findCurrentSeasonYear(wrapper.seasons()));
                    dto.setStartDate(helper.findCurrentSeasonStart(wrapper.seasons()));
                    dto.setEndDate(helper.findCurrentSeasonEnd(wrapper.seasons()));

                    return dto;
                })
                .toList();
    }

    @Override
    @Cacheable(value = "leagueById" , key = "#leagueId", unless = "#result == null")
    public LeagueDetailResponseDTO getLeagueById(int leagueId) {
        log.info(" Cache MISS ByID→ llamando a API-Football");
        ApiLeagueResponse apiResponse = client.getLeagueById(leagueId);
        LeagueWrapper wrapper = apiResponse.response().stream()
                .findFirst()
                .orElseThrow(LeagueNotFoundException::new);

        LeagueDetailResponseDTO dto = mapper.toLeagueDetailResponse(wrapper);

        dto.setSeasons(helper.getLastSeasons(wrapper.seasons()));
        dto.setCurrentSeason(helper.toCurrentSeason(wrapper.seasons()));

        return dto;
    }
}
