package com.bytecodes.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FixtureStatus {
    @JsonProperty("short")
    String fixtureShort;
    @JsonProperty("long")
    String fixtureLong;

}
