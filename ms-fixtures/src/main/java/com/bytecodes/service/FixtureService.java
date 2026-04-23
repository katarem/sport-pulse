package com.bytecodes.service;

import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.model.Fixture;
import com.bytecodes.model.LiveFixture;

import java.util.Set;

public interface FixtureService {
    Set<Fixture> getFixtures(FixtureFilters filters);
    Set<LiveFixture> getLiveFixtures();
}
