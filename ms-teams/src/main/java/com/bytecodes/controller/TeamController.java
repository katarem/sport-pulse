package com.bytecodes.controller;

import com.bytecodes.client.TeamFilter;
import com.bytecodes.dto.response.TeamResponseDTO;
import com.bytecodes.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public List<TeamResponseDTO> getTeams(@Valid @SpringQueryMap TeamFilter filter) {
        return teamService.getTeams(filter);
    }

    @GetMapping("/{teamId}")
   public TeamResponseDTO getTeam(@PathVariable("teamId") int id) {
        return teamService.getTeam(id);
    }

}
