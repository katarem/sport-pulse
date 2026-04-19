package com.bytecodes.client;

import com.bytecodes.config.LeagueConfig;
import com.bytecodes.dto.external.ApiLeagueResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "leagueClient",
        url = "${api.football.url}",
        configuration = LeagueConfig.class
)
public interface LeagueClient {
    @GetMapping("/leagues")
    ApiLeagueResponse getLeagues(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer season
    );

    @GetMapping("/leagues")
    ApiLeagueResponse getLeagueById( @RequestParam Integer id);
}
