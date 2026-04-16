package com.bytecodes.client;

import com.bytecodes.config.LeagueConfig;
import com.bytecodes.dto.external.ApiLeagueResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "leagueClient",
        url = "${api.football.url}",
        configuration = LeagueConfig.class
)
public interface LeagueFeing {
    @GetMapping("/leagues")
    ApiLeagueResponse getLeagues(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Integer season,
            @RequestParam(required = false) Integer id
    );

    @GetMapping("/leagues")
    ApiLeagueResponse getLeagueById(@RequestParam Integer id);
}
