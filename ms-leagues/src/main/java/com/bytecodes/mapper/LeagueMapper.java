package com.bytecodes.mapper;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.external.Season;
import com.bytecodes.dto.response.LeagueDetailResponse;
import com.bytecodes.dto.response.LeagueResponse;
import com.bytecodes.dto.response.SeasonResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeagueMapper {

    // ---------------------------
    // Mapeo para GET /api/leagues
    // ---------------------------
    @Mapping(source = "league.id", target = "id")
    @Mapping(source = "league.name", target = "name")
    @Mapping(source = "league.type", target = "type")
    @Mapping(source = "country.name", target = "country")
    @Mapping(source = "league.logo", target = "logo")
    LeagueResponse toLeagueResponse(LeagueWrapper wrapper);


    // ---------------------------
    // Mapeo para GET /api/leagues/{leagueid}
    // ---------------------------
    @Mapping(source = "league.id", target = "id")
    @Mapping(source = "league.name", target = "name")
    @Mapping(source = "league.type", target = "type")
    @Mapping(source = "country.name", target = "country")
    @Mapping(source = "league.logo", target = "logo")
    @Mapping(target = "seasons", ignore = true)
    @Mapping(target = "currentSeason", ignore = true)
    LeagueDetailResponse toLeagueDetailResponse(LeagueWrapper wrapper);
    // ---------------------------
    // Mapeo para currentSeason del LeagueDetail
    // ---------------------------
    @Mapping(source = "year", target = "year")
    @Mapping(source = "start", target = "startDate")
    @Mapping(source = "end", target = "endDate")
    @Mapping(source = "current", target = "current")
    SeasonResponse toCurrentSeasonResponse(Season season);


}
