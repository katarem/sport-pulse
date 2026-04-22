package com.bytecodes.client;

import com.bytecodes.dto.request.FixtureStatusValues;
import lombok.Data;

@Data
public class FixtureQueryFilters {
    private Integer league;
    private String date;
    private FixtureStatusValues status;
    private Integer season;
}
