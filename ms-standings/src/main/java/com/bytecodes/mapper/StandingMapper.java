package com.bytecodes.mapper;

import com.bytecodes.dto.internal.TeamClientDto;
import com.bytecodes.dto.response.TeamDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StandingMapper {

    TeamDto toTeamDto(TeamClientDto clientDto);


}
