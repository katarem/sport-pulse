package com.bytecodes.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record LeagueDTO(Integer id,
                        String name,
                        @JsonInclude(JsonInclude.Include.NON_NULL) String country,
                        @JsonInclude(JsonInclude.Include.NON_NULL) String season) {

}
