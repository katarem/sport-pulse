package com.bytecodes.client;

import com.bytecodes.config.FixtureClientConfig;
import com.bytecodes.dto.external.FixtureWrapperDTO;
import com.bytecodes.dto.external.LiveFixtureWrapperDTO;
import com.bytecodes.dto.response.FixtureApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@FeignClient(
        name = "fixture-client",
        url = "${api.football.url}",
        configuration = FixtureClientConfig.class
)
public interface FixtureClient {

    @GetMapping("/fixtures")
    FixtureApiResponse<Set<FixtureWrapperDTO>> getFixtures(@SpringQueryMap FixtureQueryFilters filter);

}
