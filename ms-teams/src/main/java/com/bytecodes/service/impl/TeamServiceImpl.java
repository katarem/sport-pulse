package com.bytecodes.service.impl;

import com.bytecodes.client.TeamClient;
import com.bytecodes.client.TeamFilter;
import com.bytecodes.dto.external.ApiTeamResponseDTO;
import com.bytecodes.dto.external.TeamWrapperDTO;
import com.bytecodes.dto.response.TeamResponseDTO;
import com.bytecodes.exception.TeamNotFoundException;
import com.bytecodes.mapper.TeamMapper;
import com.bytecodes.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.shaded.freemarker.template.TemplateModelException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamMapper mapper;
    private final TeamClient client;

    @Override
    @Cacheable(value = "teams",unless = "#result == null")
    public List<TeamResponseDTO> getTeams(TeamFilter filter) {

        return client.getTeams(filter).response().stream()
                .map(mapper::toTeamResponse)
                .toList();
    }

    @Override
    @Cacheable(value = "teamById",key = "#teamId",unless = "#result == null")
    public TeamResponseDTO getTeam(int teamId) {

        TeamFilter filter = TeamFilter.byId(teamId);
        ApiTeamResponseDTO teamResponse = client.getTeams(filter);
        TeamWrapperDTO wrapper = teamResponse.response().stream()
                .findFirst()
                .orElseThrow(TeamNotFoundException::new);

        return mapper.toTeamResponse(wrapper);
    }
}
