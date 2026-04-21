package com.bytecodes.mapper;

import com.bytecodes.dto.external.VenueDTO;
import com.bytecodes.model.Venue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VenueMapper {

    Venue toModel(VenueDTO dto);
}
