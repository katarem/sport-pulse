package com.bytecodes.client;

import lombok.Data;

@Data
public class FixtureQueryFilters {
    private Integer league;
    private String date;
    private String status;
    private Integer season;
    private String live;

}
