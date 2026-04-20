package com.bytecodes.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FixtureStatusDTO {
    @JsonProperty("long")
    private String fixtureLong;
    @JsonProperty("short")
    private String fixtureShort;
    private Long elapsed;
    private Long extra;
}
