package com.bytecodes.service;


import com.bytecodes.client.LeagueClient;
import com.bytecodes.client.LeagueFilter;
import com.bytecodes.dto.external.ApiLeagueResponse;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.external.Season;
import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.dto.response.SeasonResponseDTO;
import com.bytecodes.exception.LeagueNotFoundException;
import com.bytecodes.mapper.LeagueMapper;
import com.bytecodes.service.impl.LeagueServiceHelperImpl;
import com.bytecodes.service.impl.LeagueServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


import static com.bytecodes.mapper.MapperTest.getLeagueWrapperDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    LeagueClient client;

    @Mock
    LeagueMapper mapper;

    @Mock
    LeagueServiceHelperImpl helper;

    @InjectMocks
    LeagueServiceImpl service;

    @Test
    void should_return_team_by_id() {

        int teamId = 100;

        LeagueWrapper wrapper = getLeagueWrapperDTO();

        ApiLeagueResponse apiResponse = mock(ApiLeagueResponse.class);
        when(apiResponse.response()).thenReturn(List.of(wrapper));

        when(helper.getLastSeasons(anyList())).thenReturn(wrapper.seasons()
                .stream()
                .map(Season::year)
                .toList());
        
        SeasonResponseDTO seasonResponseDTO = new SeasonResponseDTO();
        when(helper.toCurrentSeason(anyList())).thenReturn(seasonResponseDTO);

        when(client.getLeagues(any(LeagueFilter.class))).thenReturn(apiResponse);

        LeagueDetailResponseDTO mapped = new LeagueDetailResponseDTO();
        when(mapper.toLeagueDetailResponse(wrapper)).thenReturn(mapped);

        // WHEN
        LeagueDetailResponseDTO result = service.getLeagueById(teamId);


        // THEN
        assertSame(mapped, result);
        assertEquals(wrapper.seasons().stream().map(Season::year).toList(), result.getSeasons());
        assertSame(seasonResponseDTO, result.getCurrentSeason());

        verify(client).getLeagues(any(LeagueFilter.class));
        verify(mapper).toLeagueDetailResponse(wrapper);
        verify(helper).getLastSeasons(anyList());
        verify(helper).toCurrentSeason(anyList());
    }

    @Test
    void should_throw_exception_when_league_not_found() {

        // GIVEN
        int teamId = 999;

        ApiLeagueResponse apiResponse = mock(ApiLeagueResponse.class);
        when(apiResponse.response()).thenReturn(List.of());

        when(client.getLeagues(any(LeagueFilter.class))).thenReturn(apiResponse);

        // WHEN + THEN
        assertThrows(LeagueNotFoundException.class, () -> service.getLeagueById(teamId));

        verify(client).getLeagues(any(LeagueFilter.class));
        verify(mapper, never()).toLeagueDetailResponse(any());
    }

}
