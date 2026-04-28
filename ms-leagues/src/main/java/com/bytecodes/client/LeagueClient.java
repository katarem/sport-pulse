package com.bytecodes.client;

import com.bytecodes.config.LeagueConfig;
import com.bytecodes.dto.external.ApiLeagueResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "leagueClient",
        url = "${api.football.url}",
        configuration = LeagueConfig.class
)
public interface LeagueClient {
    @GetMapping("/leagues")
    ApiLeagueResponseDTO getLeagues(
            @Valid @SpringQueryMap LeagueFilter filter
    );
}
