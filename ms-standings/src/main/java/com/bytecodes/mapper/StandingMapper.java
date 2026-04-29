package com.bytecodes.mapper;

import com.bytecodes.dto.external.LeagueApiDTO;
import com.bytecodes.dto.external.StandingApiDTO;
import com.bytecodes.dto.internal.TeamClientDTO;
import com.bytecodes.dto.response.LeagueDTO;
import com.bytecodes.dto.response.StandingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StandingMapper {


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

    LeagueDTO toLeagueDTO(LeagueApiDTO league);

}
