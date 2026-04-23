package com.bytecodes.client;

import com.bytecodes.config.TeamConfig;
import com.bytecodes.dto.external.ApiResponseDTO;
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
    ApiResponseDTO getTeams(
            @Valid @SpringQueryMap TeamFilter filter
    );
}
