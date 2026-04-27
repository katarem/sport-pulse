package com.bytecodes.mapper;

import com.bytecodes.dto.external.TeamWrapperDTO;
import com.bytecodes.dto.response.TeamResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(source = "team.id",target = "id")
    @Mapping(source = "team.name",target = "name")
    @Mapping(source = "team.country",target = "country")
    @Mapping(source = "team.logo",target = "logo")
    @Mapping(source = "team.founded",target = "founded")
    @Mapping(source = "team.national",target = "national")

    @Mapping(source = "venue.name",target = "stadium.name")
    @Mapping(source = "venue.address",target = "stadium.address")
    @Mapping(source = "venue.city",target = "stadium.city")
    @Mapping(source = "venue.capacity",target = "stadium.capacity")
    @Mapping(source = "venue.surface",target = "stadium.surface")
    TeamResponseDTO toTeamResponse(TeamWrapperDTO teamDTO);
}
