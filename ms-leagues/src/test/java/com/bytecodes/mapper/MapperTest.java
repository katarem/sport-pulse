package com.bytecodes.mapper;


import com.bytecodes.dto.external.Country;
import com.bytecodes.dto.external.League;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.external.Season;
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
            League league = new League(
                    100,
                    "Real Madrid",
                    "RMA",
                    "logo.png"

            );

            Country country = new Country(
                    "Spain",
                    "Code",
                    "una flag"

            );

            List<Season> seasons = List.of(new Season(
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
        Season season = new Season(
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




