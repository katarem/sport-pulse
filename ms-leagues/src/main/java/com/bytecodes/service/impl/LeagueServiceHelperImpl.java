package com.bytecodes.service.impl;

import com.bytecodes.dto.external.Season;
import com.bytecodes.dto.response.SeasonResponseDTO;
import com.bytecodes.mapper.LeagueMapper;
import com.bytecodes.service.LeagueServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
@Service
@RequiredArgsConstructor
public class LeagueServiceHelperImpl implements LeagueServiceHelper {

    private final LeagueMapper mapper;

    @Override
    public Integer findCurrentSeasonYear(List<Season> seasons) {
        return seasons.stream()
                .filter(Season::current)
                .map(Season::year)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String findCurrentSeasonStart(List<Season> seasons) {
        return seasons.stream()
                .filter(Season::current)
                .map(Season::start)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String findCurrentSeasonEnd(List<Season> seasons) {
        return seasons.stream()
                .filter(Season::current)
                .map(Season::end)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public SeasonResponseDTO toCurrentSeason(List<Season> seasons) {
        return seasons.stream()
                .filter(Season::current)
                .findFirst()
                .map(mapper::toCurrentSeasonResponse)
                .orElse(null);
    }

    @Override
    public List<Integer> getLastSeasons(List<Season> seasons) {
        return seasons.stream()
                .map(Season::year)
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .sorted()
                .toList();
    }

}
