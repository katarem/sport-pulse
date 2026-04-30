package com.bytecodes.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.bytecodes.client.MsTeamClient;
import com.bytecodes.client.StandingClient;
import com.bytecodes.client.StandingFilter;
import com.bytecodes.dto.external.ApiStandingResponseDTO;
import com.bytecodes.dto.external.LeagueApiDTO;
import com.bytecodes.dto.external.StandingApiDTO;
import com.bytecodes.dto.internal.TeamClientDTO;
import com.bytecodes.dto.response.LeagueDTO;
import com.bytecodes.dto.response.StandingResponseDTO;
import com.bytecodes.dto.response.StandingResponseDetailDTO;
import com.bytecodes.mapper.StandingMapper;
import com.bytecodes.service.StandingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StandingServiceImpl implements StandingService {

    private final StandingClient standingClient;
    private final MsTeamClient teamClient;
    private final StandingMapper mapper;


    @Override
    public List<StandingApiDTO> extractStandings(ApiStandingResponseDTO response) {

        if (response.response().isEmpty()) {
            throw new IllegalStateException("API devolvió response vacío");
        }

        var league = response.response().get(0).leagueApiDTO();
        if (league == null) {
            throw new IllegalStateException("leagueApiDTO es null");
        }

        if (league.standings().isEmpty()) {
            throw new IllegalStateException("standings está vacío");
        }

        return league.standings().get(0);
    }


    @Override
    @Cacheable(
            value = "standings",
            key = "#filter.league + '-' + #filter.season",
            unless = "#result == null"
    )
    public StandingResponseDTO getStandings(StandingFilter filter) {

        ApiStandingResponseDTO apiResponse = standingClient.getStanding(filter);
        TeamClientDTO msTeam = teamClient.getTeams(filter).get(0);

        List<StandingApiDTO> standings = extractStandings(apiResponse);


        var items = standings.stream()
                .map(standing -> {
                    return mapper.standingApiAndTeamClientToResponseDto(standing, msTeam);
                })
                .toList();

        // Construir LeagueDTO
        LeagueApiDTO league = apiResponse.response().get(0).leagueApiDTO();
        LeagueDTO leagueDTO = mapper.toLeagueDTO(league);

        // Construir respuesta final
        StandingResponseDTO response = new StandingResponseDTO();
        response.setLeague(leagueDTO);
        response.setStadings(items);

        return response;
    }

    @Override
    @Cacheable(
            value = "standings",
            key = "#filter.league + '-' + #filter.season + '-' + #teamId",
            unless = "#result == null"
    )
    public StandingResponseDetailDTO getStandingXTeam(StandingFilter filter , Integer teamId) {
        TeamClientDTO msTeam = teamClient.getTeam(teamId);
        ApiStandingResponseDTO apiResponse = standingClient.getStanding(filter);
        List<StandingApiDTO> standing = extractStandings(apiResponse);

        LeagueApiDTO league = apiResponse.response().get(0).leagueApiDTO();
        return mapper.standingApiAndTeamClientAndLeagueToDeatailResponseDto(standing.get(0),msTeam,league);
    }

}
