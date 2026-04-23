package com.bytecodes.mapper;

import com.bytecodes.dto.external.TeamDTO;
import com.bytecodes.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamMapper {

    Team toModel(TeamDTO dto);

}
