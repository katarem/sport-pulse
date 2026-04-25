package com.bytecodes.client;

import com.bytecodes.config.StandingConfig;
import com.bytecodes.dto.external.ApiResponseDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "teamClient",
        url = "${api.football.url}",
        configuration = StandingConfig.class
)

public interface StandingClient {

    @GetMapping("standings")
    ApiResponseDTO getStanding(
            @Valid @SpringQueryMap StandingFilter filter
    );
}
