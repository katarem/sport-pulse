package com.bytecodes.service.impl;

import com.bytecodes.dto.external.SeasonDTO;
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
    public Integer findCurrentSeasonYear(List<SeasonDTO> seasons) {
        return seasons.stream()
                .filter(SeasonDTO::current)
                .map(SeasonDTO::year)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String findCurrentSeasonStart(List<SeasonDTO> seasons) {
        return seasons.stream()
                .filter(SeasonDTO::current)
                .map(SeasonDTO::start)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String findCurrentSeasonEnd(List<SeasonDTO> seasons) {
        return seasons.stream()
                .filter(SeasonDTO::current)
                .map(SeasonDTO::end)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Override
    public SeasonResponseDTO toCurrentSeason(List<SeasonDTO> seasons) {
        return seasons.stream()
                .filter(SeasonDTO::current)
                .findFirst()
                .map(mapper::toCurrentSeasonResponse)
                .orElse(null);
    }

    @Override
    public List<Integer> getLastSeasons(List<SeasonDTO> seasons) {
        return seasons.stream()
                .map(SeasonDTO::year)
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .sorted()
                .toList();
    }

}
