package com.bytecodes.model;

import lombok.Data;

@Data
public class FixtureEvent {
    private Long elapsed;
    private String type;
    private String detail;
    private EventTeam team;
    private EventPlayer player;
    private EventPlayer assist;
}
