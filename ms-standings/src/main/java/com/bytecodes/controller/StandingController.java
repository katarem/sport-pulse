package com.bytecodes.controller;

import com.bytecodes.client.StandingFilter;
import com.bytecodes.dto.response.StandingResponseDTO;
import com.bytecodes.dto.response.StandingResponseDetailDTO;
import com.bytecodes.service.StandingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standings")
@RequiredArgsConstructor
public class StandingController {

    private final StandingService standingService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER')")
    public StandingResponseDTO getStandings(@Valid StandingFilter filter) {
        return standingService.getStandings(filter);
    }

    @GetMapping("/team/{teamId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public StandingResponseDetailDTO getStandings(StandingFilter filter,
                                                  @PathVariable Integer teamId) {
        return standingService.getStandingByTeam(filter,teamId);
    }

}
