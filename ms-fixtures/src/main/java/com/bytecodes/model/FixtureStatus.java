package com.bytecodes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FixtureStatus {
    @JsonProperty("short")
    private String fixtureShort;
    @JsonProperty("long")
    private String fixtureLong;

}
