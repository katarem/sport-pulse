package com.bytecodes.mapper;

import com.bytecodes.dto.external.FixtureStatusDTO;
import com.bytecodes.model.FixtureStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FixtureStatusMapper {

    FixtureStatus toModel(FixtureStatusDTO dto);

}
