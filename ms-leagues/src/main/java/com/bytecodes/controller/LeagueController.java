package com.bytecodes.controller;


import com.bytecodes.dto.response.LeagueDetailResponse;
import com.bytecodes.dto.response.LeagueResponse;
import com.bytecodes.service.LeagueService;
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
    public List<LeagueResponse> getLeagues(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer season
    ) {
        return leagueService.getLeagues(country, season);
    }

    @GetMapping("/{leagueId}")
    public LeagueDetailResponse getLeagueById(@PathVariable("leagueId") int id) {
        return leagueService.getLeagueById(id);
    }
}
