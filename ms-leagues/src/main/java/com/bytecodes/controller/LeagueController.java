package com.bytecodes.controller;


import com.bytecodes.client.LeagueFilter;
import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.service.LeagueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @Operation(summary = "Endpoint para obtener ligas con filtros")
    @SecurityRequirement(name = "JWT")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<LeagueResponseDTO> getLeagues(
            @Valid @SpringQueryMap LeagueFilter filter) {
        return leagueService.getLeagues(filter);
    }

    @Operation(summary = "Endpoint para obtener ligas por Id")
    @SecurityRequirements(value = {
        @SecurityRequirement(name = "JWT"),
        @SecurityRequirement(name = "Internal Api Key")
    })
    @GetMapping("/{leagueId}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public LeagueDetailResponseDTO getLeagueById(@PathVariable("leagueId") int id) {
        return leagueService.getLeagueById(id);
    }
}
