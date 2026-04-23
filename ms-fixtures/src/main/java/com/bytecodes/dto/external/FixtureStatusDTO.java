package com.bytecodes.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FixtureStatusDTO {
    @JsonProperty("short")
    private String fixtureShort;
    @JsonProperty("long")
    private String fixtureLong;
}
