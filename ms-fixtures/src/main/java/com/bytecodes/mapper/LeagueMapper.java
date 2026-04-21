package com.bytecodes.mapper;

import com.bytecodes.dto.external.LeagueDTO;
import com.bytecodes.model.League;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeagueMapper {

    League mapToModel(LeagueDTO dto);


}
