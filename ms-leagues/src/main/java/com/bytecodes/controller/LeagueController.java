package com.bytecodes.controller;


import com.bytecodes.client.LeagueFilter;
import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.service.LeagueService;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public List<LeagueResponseDTO> getLeagues(
            @Valid @SpringQueryMap LeagueFilter filter) {
        return leagueService.getLeagues(filter);
    }

    @GetMapping("/{leagueId}")
    public LeagueDetailResponseDTO getLeagueById(@PathVariable("leagueId") int id) {
        return leagueService.getLeagueById(id);
    }
}
