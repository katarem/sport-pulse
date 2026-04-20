package com.bytecodes.dto.external;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class FixtureDTO {
    private Long id;
    private String referee;
    private String timezone;
    private ZonedDateTime date;
    private Map<String, Float> periods;
    private VenueDTO venue;
    private FixtureStatusDTO status;
}
