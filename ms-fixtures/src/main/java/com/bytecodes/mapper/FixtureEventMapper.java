package com.bytecodes.mapper;

import com.bytecodes.dto.external.FixtureEventDTO;
import com.bytecodes.model.FixtureEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        TeamMapper.class,
        EventPlayerMapper.class
})
public interface FixtureEventMapper {

    @Mapping(source = "time.elapsed", target = "elapsed")
    FixtureEvent toModel(FixtureEventDTO dto);

}
