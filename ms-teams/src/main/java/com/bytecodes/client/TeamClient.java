package com.bytecodes.client;

import com.bytecodes.config.TeamConfig;
import com.bytecodes.dto.external.ApiTeamResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "teamClient",
        url = "${api.football.url}",
        configuration = TeamConfig.class
)

public interface TeamClient {

    @GetMapping("teams")
    ApiTeamResponseDTO getTeams(
            @Valid @SpringQueryMap TeamFilter filter
    );
}
