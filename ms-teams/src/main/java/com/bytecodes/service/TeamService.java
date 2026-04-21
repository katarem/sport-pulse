package com.bytecodes.service;

import com.bytecodes.client.TeamFilter;
import com.bytecodes.dto.response.TeamResponseDTO;

import java.util.List;

public interface TeamService {

    List<TeamResponseDTO> getTeams(TeamFilter filter);
    TeamResponseDTO getTeam(int teamId);
}
