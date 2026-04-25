package com.bytecodes.mapper;

import com.bytecodes.dto.external.EventPlayerDTO;
import com.bytecodes.model.EventPlayer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventPlayerMapper {

    EventPlayer toModel(EventPlayerDTO dto);


}
