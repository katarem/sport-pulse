package com.bytecodes.mapper;


import com.bytecodes.dto.external.TeamDTO;
import com.bytecodes.dto.external.TeamWrapperDTO;
import com.bytecodes.dto.external.VenueDTO;
import com.bytecodes.dto.response.TeamResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    private final TeamMapperImpl mapper = new TeamMapperImpl();

    @Test
    void should_map_team_wrapper_to_team_response(){

        // GIVEN
        TeamWrapperDTO wrapper = getTeamWrapperDTO();

        // WHEN
        TeamResponseDTO dto = mapper.toTeamResponse(wrapper);

        // THEN
        assertEquals(100, dto.getId());
        assertEquals("Real Madrid", dto.getName());
        assertEquals("Spain", dto.getCountry());
        assertEquals("logo.png", dto.getLogo());
        assertEquals(1902, dto.getFounded());
        assertFalse(dto.getNational());

        assertNotNull(dto.getStadium());
        assertEquals("Santiago Bernabéu", dto.getStadium().getName());
        assertEquals("Av. Concha Espina", dto.getStadium().getAddress());
        assertEquals("Madrid", dto.getStadium().getCity());
        assertEquals(81000, dto.getStadium().getCapacity());
        assertEquals("grass", dto.getStadium().getSurface());
    }

    public static TeamWrapperDTO getTeamWrapperDTO() {
        TeamDTO team = new TeamDTO(
                100,
                "Real Madrid",
                "RMA",
                "Spain",
                1902,
                false,
                "logo.png"
        );

        VenueDTO venue = new VenueDTO(
                1,
                "Santiago Bernabéu",
                "Av. Concha Espina",
                "Madrid",
                81000,
                "grass",
                "image.png"
        );

        return new TeamWrapperDTO(team, venue);
    }
}

