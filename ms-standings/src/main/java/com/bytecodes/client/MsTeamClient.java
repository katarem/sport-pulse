package com.bytecodes.client;

import com.bytecodes.dto.response.TeamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ms-teams",url = "${ms.teams.url}")
public interface MsTeamClient {

    @GetMapping("/api/teams/{teamId}")
    TeamDto getTeamById(@PathVariable Integer teamId);


}
