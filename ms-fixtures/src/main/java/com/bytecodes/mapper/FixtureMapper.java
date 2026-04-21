package com.bytecodes.mapper;

import com.bytecodes.client.FixtureQueryFilters;
import com.bytecodes.dto.external.FixtureDTO;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.model.Fixture;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {FixtureStatusMapper.class, LeagueMapper.class, VenueMapper.class})
public interface FixtureMapper {

    Fixture toModel(FixtureDTO dto);

    FixtureQueryFilters toClientFilters(FixtureFilters filters);

}
