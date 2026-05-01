package com.bytecodes.mapper;


import com.bytecodes.dto.external.CountryDTO;
import com.bytecodes.dto.external.LeagueDTO;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.external.SeasonDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.dto.response.SeasonResponseDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MapperTest {

    private final LeagueMapperImpl mapper = new LeagueMapperImpl();

    @Test
    void should_map_wrapper_to_league_response() {

        // GIVEN
        LeagueWrapper wrapper = getLeagueWrapperDTO();

        // WHEN
        LeagueResponseDTO dto = mapper.toLeagueResponse(wrapper);

        // THEN
        assertEquals(100, dto.getId());
        assertEquals("Real Madrid", dto.getName());
        assertEquals("Spain", dto.getCountry());
        assertEquals("logo.png", dto.getLogo());

    }

        public static LeagueWrapper getLeagueWrapperDTO () {
            LeagueDTO league = new LeagueDTO(
                    100,
                    "Real Madrid",
                    "RMA",
                    "logo.png"

            );

            CountryDTO country = new CountryDTO(
                    "Spain",
                    "Code",
                    "una flag"

            );

            List<SeasonDTO> seasons = List.of(new SeasonDTO(
                    2025,
                    "2025-04-26",
                    "2025-04-27",
                    true


            ));

            return new LeagueWrapper(league, country, seasons);
        }

    @Test
    void should_map_season_to_season_response() {

        // GIVEN
        SeasonDTO season = new SeasonDTO(
                2025,
                "2025-04-26",
                "2025-04-27",
                true
        );

        // WHEN
        SeasonResponseDTO dto = mapper.toCurrentSeasonResponse(season);

        // THEN
        assertEquals(2025, dto.getYear());
        assertEquals("2025-04-26", dto.getStartDate());
        assertEquals("2025-04-27", dto.getEndDate());

    }

}




