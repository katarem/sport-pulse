package com.bytecodes.controller;

import com.bytecodes.model.FixtureEvent;
import com.bytecodes.model.LiveFixture;
import com.bytecodes.service.FixtureService;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.model.Fixture;
import com.bytecodes.util.FilterUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Endpoint para obtener partidos con filtros")
    @SecurityRequirement(name = "JWT")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    Set<Fixture> getFixtures(@Valid FixtureFilters filters) {
        return service.getFixtures(FilterUtil.resolveFilters(filters));
    }

    @Operation(summary = "Endpoint para obtener partidos en juego")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/live")
    @PreAuthorize("hasRole('USER')")
    Set<LiveFixture> getLiveFixtures() {
        return service.getLiveFixtures();
    }
    @Operation(summary = "Endpoint para obtener eventos de un partido")
    @SecurityRequirement(name = "JWT")
    @GetMapping("/{fixtureId}/events")
    @PreAuthorize("hasRole('USER')")
    Set<FixtureEvent> getEvents(@PathVariable Long fixtureId){
        return service.getFixtureEvents(fixtureId);
    }
}
