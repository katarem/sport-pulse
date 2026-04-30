package com.bytecodes.client;

import com.bytecodes.dto.internal.TeamClientDTO;
import com.bytecodes.dto.response.TeamDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="ms-teams",url = "${ms.teams.url}")
public interface MsTeamClient {

    @GetMapping("/api/teams")
    List<TeamClientDTO> getTeams(@Valid @SpringQueryMap StandingFilter filter);

    @GetMapping("/api/teams/{teamId}")
    TeamClientDTO getTeam(@PathVariable("teamId") Integer id);


}
