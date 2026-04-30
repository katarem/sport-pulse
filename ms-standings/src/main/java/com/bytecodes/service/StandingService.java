package com.bytecodes.service;

import com.bytecodes.client.StandingFilter;
import com.bytecodes.dto.external.ApiStandingResponseDTO;
import com.bytecodes.dto.external.StandingApiDTO;
import com.bytecodes.dto.response.StandingDTO;
import com.bytecodes.dto.response.StandingResponseDTO;
import com.bytecodes.dto.response.StandingResponseDetailDTO;

import java.util.List;

public interface StandingService {

    List<StandingApiDTO> extractStandings(ApiStandingResponseDTO response);
    StandingResponseDTO getStandings(StandingFilter filter);
    StandingResponseDetailDTO getStandingXTeam(StandingFilter filter, Integer teamId);


}
