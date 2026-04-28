package com.bytecodes.service.impl;

import com.bytecodes.client.LeagueClient;
import com.bytecodes.client.LeagueFilter;
import com.bytecodes.dto.external.ApiLeagueResponseDTO;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.exception.LeagueNotFoundException;
import com.bytecodes.mapper.LeagueMapper;
import com.bytecodes.service.LeagueService;
import com.bytecodes.service.LeagueServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LeagueServiceImpl implements LeagueService {

    private final LeagueClient client;
    private final LeagueMapper mapper;
    private final LeagueServiceHelper helper;

    @Cacheable(value = "leagues", unless = "#result == null")
    @Override
    public List<LeagueResponseDTO> getLeagues(LeagueFilter filter) {

        return client.getLeagues(filter).response().stream()
                .map(wrapper -> {
                    LeagueResponseDTO dto = mapper.toLeagueResponse(wrapper);

                    dto.setCurrentSeason(helper.findCurrentSeasonYear(wrapper.seasons()));
                    dto.setStartDate(helper.findCurrentSeasonStart(wrapper.seasons()));
                    dto.setEndDate(helper.findCurrentSeasonEnd(wrapper.seasons()));

                    return dto;
                    // Aqui hacemos un filtro para quitar los objetos null en currentSeason
                }).filter(dto -> dto.getCurrentSeason() != null)
                .toList();
    }

    @Override
    @Cacheable(value = "leagueById" , key = "#leagueId", unless = "#result == null")
    public LeagueDetailResponseDTO getLeagueById(int leagueId) {
        LeagueFilter filter = LeagueFilter.byId(leagueId);
        ApiLeagueResponseDTO apiResponse = client.getLeagues(filter);
        LeagueWrapper wrapper = apiResponse.response().stream()
                .findFirst()
                .orElseThrow(LeagueNotFoundException::new);

        LeagueDetailResponseDTO dto = mapper.toLeagueDetailResponse(wrapper);
        dto.setSeasons(helper.getLastSeasons(wrapper.seasons()));
        dto.setCurrentSeason(helper.toCurrentSeason(wrapper.seasons()));

        return dto;
    }
}
