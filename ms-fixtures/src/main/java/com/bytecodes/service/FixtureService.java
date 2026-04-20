package com.bytecodes.service;

import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.model.Fixture;

import java.util.Set;

public interface FixtureService {
    Set<Fixture> getFixtures(FixtureFilters filters);
}
