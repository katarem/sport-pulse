package com.bytecodes.service;

import com.bytecodes.client.TeamClient;
import com.bytecodes.client.TeamFilter;
import com.bytecodes.dto.external.ApiTeamResponseDTO;
import com.bytecodes.dto.external.TeamDTO;
import com.bytecodes.dto.external.TeamWrapperDTO;
import com.bytecodes.dto.external.VenueDTO;
import com.bytecodes.dto.response.TeamResponseDTO;
import com.bytecodes.exception.TeamNotFoundException;
import com.bytecodes.mapper.TeamMapper;
import com.bytecodes.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.bytecodes.mapper.MapperTest.getTeamWrapperDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    TeamClient client;

    @Mock
    TeamMapper mapper;

    @InjectMocks
    TeamServiceImpl service;

    @Test
    void should_return_list_of_teams() {

        // GIVEN
        TeamFilter filter = new TeamFilter();

        TeamWrapperDTO wrapper1 = new TeamWrapperDTO(new TeamDTO(
                100,
                "Real Madrid",
                "RMA",
                "Spain",
                1902,
                false,
                "logo.png"), new VenueDTO(
                1,
                "Santiago Bernabéu",
                "Av. Concha Espina",
                "Madrid",
                81000,
                "grass",
                "image.png"
        ));
        TeamWrapperDTO wrapper2 = new TeamWrapperDTO(new TeamDTO(101,
                "Madrid",
                "RMA",
                "Spain",
                1902,
                false,
                "logo.png"),new VenueDTO(2,
                "Santiago ",
                "Av. Concha Espina",
                "Madrid",
                81000,
                "grass",
                "image.png"));

        ApiTeamResponseDTO apiResponse = mock(ApiTeamResponseDTO.class);
        when(apiResponse.response()).thenReturn(List.of(wrapper1, wrapper2));

        when(client.getTeams(filter)).thenReturn(apiResponse);

        TeamResponseDTO dto1 = new TeamResponseDTO();
        TeamResponseDTO dto2 = new TeamResponseDTO();

        when(mapper.toTeamResponse(wrapper1)).thenReturn(dto1);
        when(mapper.toTeamResponse(wrapper2)).thenReturn(dto2);

        // WHEN
        List<TeamResponseDTO> result = service.getTeams(filter);

        // THEN
        assertEquals(2, result.size());
        assertSame(dto1, result.get(0));
        assertSame(dto2, result.get(1));

        verify(client).getTeams(filter);
        verify(mapper).toTeamResponse(wrapper1);
        verify(mapper).toTeamResponse(wrapper2);
    }

    @Test
    void should_return_team_by_id() {

        int teamId = 100;

        TeamWrapperDTO wrapper = getTeamWrapperDTO();

        ApiTeamResponseDTO apiResponse = mock(ApiTeamResponseDTO.class);
        when(apiResponse.response()).thenReturn(List.of(wrapper));

        when(client.getTeams(any(TeamFilter.class))).thenReturn(apiResponse);

        TeamResponseDTO mapped = new TeamResponseDTO();
        when(mapper.toTeamResponse(wrapper)).thenReturn(mapped);

        // WHEN
        TeamResponseDTO result = service.getTeam(teamId);

        // THEN
        assertSame(mapped, result);

        verify(client).getTeams(any(TeamFilter.class));
        verify(mapper).toTeamResponse(wrapper);
    }

    @Test
    void should_throw_exception_when_team_not_found() {

        // GIVEN
        int teamId = 999;

        ApiTeamResponseDTO apiResponse = mock(ApiTeamResponseDTO.class);
        when(apiResponse.response()).thenReturn(List.of());

        when(client.getTeams(any(TeamFilter.class))).thenReturn(apiResponse);

        // WHEN + THEN
        assertThrows(TeamNotFoundException.class, () -> service.getTeam(teamId));

        verify(client).getTeams(any(TeamFilter.class));
        verify(mapper, never()).toTeamResponse(any());
    }

}
