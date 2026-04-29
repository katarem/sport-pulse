package com.bytecodes.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseItemDTO(
        @JsonProperty("league") LeagueApiDTO leagueApiDTO , @JsonProperty("standings") StandingApiDTO standingApiDTO) {

}
