package com.bytecodes.controller;

import com.bytecodes.client.StandingFilter;
import com.bytecodes.dto.response.StandingResponseDTO;
import com.bytecodes.service.StandingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/standings")
@RequiredArgsConstructor
public class StandingController {

    private final StandingService standingService;

    @GetMapping
    public StandingResponseDTO getStandings(@Valid @SpringQueryMap StandingFilter filter) {
        return standingService.getStandings(filter);
    }
}
