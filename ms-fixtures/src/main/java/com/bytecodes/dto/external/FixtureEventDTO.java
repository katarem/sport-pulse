package com.bytecodes.dto.external;

import lombok.Data;

@Data
public class FixtureEventDTO {
    private String type;
    private String detail;
    private EventTimeDTO time;
    private TeamDTO team;
    private EventPlayerDTO player;
    private EventPlayerDTO assist;
}
