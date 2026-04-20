package com.bytecodes.controller;

import com.bytecodes.service.FixtureService;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.model.Fixture;
import com.bytecodes.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/api/fixtures")
@RequiredArgsConstructor
public class FixturesController {

    private final FixtureService service;

    @GetMapping
    Set<Fixture> getFixtures(@Valid FixtureFilters filters) {
        if(Objects.isNull(filters.getDate()))
            filters.setDate(DateUtil.getTodaySimpleDate());
        return service.getFixtures(filters);
    }
}
