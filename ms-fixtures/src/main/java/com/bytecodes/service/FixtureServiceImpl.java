package com.bytecodes.service;

import com.bytecodes.client.FixtureClient;
import com.bytecodes.client.FixtureQueryFilters;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.mapper.FixtureMapper;
import com.bytecodes.mapper.TeamMapper;
import com.bytecodes.model.Fixture;
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
    private final TeamMapper teamMapper;

    @Override
    @Cacheable(value = "fixtures", key = "{#filters.date, #filters}")
    public Set<Fixture> getFixtures(FixtureFilters filters) {
        log.info("Getting the result");
        FixtureQueryFilters clientFilters = fixtureMapper.toClientFilters(filters);

        var response = client.getFixtures(clientFilters);
        if(!response.getErrors().isEmpty())
            throw new RuntimeException("Error de la api");

        return response.getResponse().stream()
                .map(fixtureWrapperDTO -> {

                    var fixture = fixtureMapper.toModel(fixtureWrapperDTO.getFixture());

                    var homeTeamDTO = fixtureWrapperDTO.getTeams().getOrDefault("home", null);
                    var awayTeamDTO = fixtureWrapperDTO.getTeams().getOrDefault("away", null);

                    if(Objects.nonNull(homeTeamDTO)) {
                        fixture.setHomeTeam(teamMapper.toModel(homeTeamDTO));
                        fixture.getHomeTeam().setGoals(fixtureWrapperDTO.getGoals().getOrDefault("home", null));
                    }
                    if(Objects.nonNull(awayTeamDTO)) {
                        fixture.setAwayTeam(teamMapper.toModel(awayTeamDTO));
                        fixture.getAwayTeam().setGoals(fixtureWrapperDTO.getGoals().getOrDefault("away", null));
                    }

                    return fixture;
                }).collect(Collectors.toSet());
    }
}
