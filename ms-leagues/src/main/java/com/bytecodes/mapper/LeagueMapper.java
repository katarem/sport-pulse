package com.bytecodes.mapper;
import com.bytecodes.dto.external.LeagueWrapper;
import com.bytecodes.dto.external.SeasonDTO;
import com.bytecodes.dto.response.LeagueDetailResponseDTO;
import com.bytecodes.dto.response.LeagueResponseDTO;
import com.bytecodes.dto.response.SeasonResponseDTO;
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
    LeagueResponseDTO toLeagueResponse(LeagueWrapper wrapper);


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
    LeagueDetailResponseDTO toLeagueDetailResponse(LeagueWrapper wrapper);
    // ---------------------------
    // Mapeo para currentSeason del LeagueDetail
    // ---------------------------
    @Mapping(source = "year", target = "year")
    @Mapping(source = "start", target = "startDate")
    @Mapping(source = "end", target = "endDate")
    @Mapping(source = "current", target = "current")
    SeasonResponseDTO toCurrentSeasonResponse(SeasonDTO season);


}
