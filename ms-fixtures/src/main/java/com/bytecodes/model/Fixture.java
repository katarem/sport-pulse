package com.bytecodes.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Fixture {
    private Long id;
    private ZonedDateTime date;
    private FixtureStatus status;
    private Team homeTeam;
    private Team awayTeam;
    private League league;
    private Venue venue;
}
