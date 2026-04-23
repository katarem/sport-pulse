package com.bytecodes.service;

import com.bytecodes.client.FixtureClient;
import com.bytecodes.client.FixtureQueryFilters;
import com.bytecodes.dto.external.FixtureEventDTO;
import com.bytecodes.dto.external.FixtureWrapperDTO;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.dto.response.FixtureApiResponse;
import com.bytecodes.mapper.FixtureEventMapper;
import com.bytecodes.mapper.FixtureMapper;
import com.bytecodes.mapper.LeagueMapper;
import com.bytecodes.mapper.TeamMapper;
import com.bytecodes.model.Fixture;
import com.bytecodes.model.FixtureEvent;
import com.bytecodes.model.LiveFixture;
import com.bytecodes.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FixtureServiceImpl implements FixtureService {

    private final FixtureClient client;
    private final FixtureMapper fixtureMapper;
    private final FixtureEventMapper fixtureEventMapper;
    private final TeamMapper teamMapper;
    private final LeagueMapper leagueMapper;

    @Override
    @Cacheable(value = "fixtures", key = "{#filters.date, #filters}")
    public Set<Fixture> getFixtures(FixtureFilters filters) {
        log.info("Getting the result");
        FixtureQueryFilters clientFilters = fixtureMapper.toClientFilters(filters);

        var response = client.getFixtures(clientFilters);

        ApiUtil.checkError(response);

        return response.getResponse().stream()
                .map(this::processFixture).collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = "fixtures")
    public Set<LiveFixture> getLiveFixtures() {

        var filters = new FixtureQueryFilters();
        filters.setLive("all");
        filters.setStatus("1H-HT-2H");

        var response = client.getFixtures(filters);

        ApiUtil.checkError(response);

        return response.getResponse().stream()
                .map(this::processLiveFixture).collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = "fixtures", key = "#fixtureId")
    public Set<FixtureEvent> getFixtureEvents(Long fixtureId) {
        FixtureApiResponse<Set<FixtureEventDTO>> response = client.getFixtureEvents(fixtureId);

        ApiUtil.checkError(response);

        return response.getResponse().stream()
                .map(fixtureEventMapper::toModel).collect(Collectors.toSet());
    }



    private LiveFixture processLiveFixture(FixtureWrapperDTO wrapperDTO) {

        var fixture = processFixture(wrapperDTO);
        var liveFixture = fixtureMapper.mapToLive(fixture);

        liveFixture.setElapsed(wrapperDTO.getFixture().getStatus().getElapsed());

        return liveFixture;
    }

    private Fixture processFixture(FixtureWrapperDTO fixtureWrapperDTO) {

        var fixture = fixtureMapper.toModel(fixtureWrapperDTO.getFixture());

        var homeTeamDTO = fixtureWrapperDTO.getTeams().getOrDefault("home", null);
        var awayTeamDTO = fixtureWrapperDTO.getTeams().getOrDefault("away", null);

        if (Objects.nonNull(homeTeamDTO)) {
            fixture.setHomeTeam(teamMapper.toModel(homeTeamDTO));
            fixture.getHomeTeam().setGoals(fixtureWrapperDTO.getGoals().getOrDefault("home", null));
        }

        if (Objects.nonNull(awayTeamDTO)) {
            fixture.setAwayTeam(teamMapper.toModel(awayTeamDTO));
            fixture.getAwayTeam().setGoals(fixtureWrapperDTO.getGoals().getOrDefault("away", null));
        }

        if (Objects.nonNull(fixtureWrapperDTO.getLeague())) {
            var league = leagueMapper.mapToModel(fixtureWrapperDTO.getLeague());
            fixture.setLeague(league);
        }

        return fixture;
    }

}
