package com.bytecodes.mapper;

import com.bytecodes.dto.external.LeagueApiDTO;
import com.bytecodes.dto.external.StandingApiDTO;
import com.bytecodes.dto.internal.TeamClientDTO;
import com.bytecodes.dto.response.LeagueDTO;
import com.bytecodes.dto.response.StandingDTO;
import com.bytecodes.dto.response.StandingResponseDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandingMapper {


     //  Para el Get del /api/standings"
    @Mapping(target = "team.id",source = "clientDTO.id")
    @Mapping(target = "team.name",source = "clientDTO.name")
    @Mapping(target = "team.logo",source = "clientDTO.logo")

    @Mapping(target = "rank",source = "standingApiDTO.rank")
    @Mapping(target = "points",source = "standingApiDTO.rank")
    @Mapping(target = "played",source = "standingApiDTO.all.played")
    @Mapping(target = "won",source = "standingApiDTO.all.win")
    @Mapping(target = "drawn",source = "standingApiDTO.all.draw")
    @Mapping(target = "lost",source = "standingApiDTO.all.lost")
    @Mapping(target = "goalsFor",source = "standingApiDTO.all.goals.goalsFor")
    @Mapping(target = "goalsAgainst",source = "standingApiDTO.all.goals.against")
    @Mapping(target = "goalDifference",source = "standingApiDTO.goalsDiff")
    @Mapping(target = "form",source = "standingApiDTO.form")
    StandingDTO standingApiAndTeamClientToResponseDto(StandingApiDTO standingApiDTO, TeamClientDTO clientDTO);


    // responde al endpoint GET/api/standing/team/{teamid}

    @Mapping(target = "team.id",source = "clientDTO.id")
    @Mapping(target = "team.name",source = "clientDTO.name")
    @Mapping(target = "team.logo", ignore = true)

    @Mapping(target = "league.id",source = "leagueApi.id")
    @Mapping(target = "league.name",source = "leagueApi.name")
    @Mapping(target = "league.country", ignore = true)
    @Mapping(target = "league.season", ignore = true)

    @Mapping(target = "season",source = "leagueApi.season")
    @Mapping(target = "rank",source = "standingApi.rank")
    @Mapping(target = "played",source = "standingApi.all.played")
    @Mapping(target = "points",source = "standingApi.points")
    @Mapping(target = "form",source = "standingApi.form")
    @Mapping(target = "description",source = "standingApi.description")
    StandingResponseDetailDTO standingApiAndTeamClientAndLeagueToDeatailResponseDto(StandingApiDTO standingApi,
                                                                                    TeamClientDTO clientDTO,
                                                                                    LeagueApiDTO leagueApi);


    LeagueDTO toLeagueDTO(LeagueApiDTO league);

}
