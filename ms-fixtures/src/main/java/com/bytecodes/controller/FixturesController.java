package com.bytecodes.controller;

import com.bytecodes.model.FixtureEvent;
import com.bytecodes.model.LiveFixture;
import com.bytecodes.service.FixtureService;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.model.Fixture;
import com.bytecodes.util.FilterUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/fixtures")
@RequiredArgsConstructor
public class FixturesController {

    private final FixtureService service;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    Set<Fixture> getFixtures(@Valid FixtureFilters filters) {
        return service.getFixtures(FilterUtil.resolveFilters(filters));
    }

    @GetMapping("/live")
    @PreAuthorize("hasRole('USER')")
    Set<LiveFixture> getLiveFixtures() {
        return service.getLiveFixtures();
    }

    @GetMapping("/{fixtureId}/events")
    @PreAuthorize("hasRole('USER')")
    Set<FixtureEvent> getEvents(@PathVariable Long fixtureId){
        return service.getFixtureEvents(fixtureId);
    }
}
