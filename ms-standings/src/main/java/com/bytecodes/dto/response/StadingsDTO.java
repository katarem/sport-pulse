package com.bytecodes.dto.response;


public record StadingsDTO (
        Integer rank,
        TeamDto team,
        Integer points,
        Integer played,
        Integer won,
        Integer drawn,
        Integer lost,
        Integer goalsFor,
        Integer goalsAgainst,
        Integer goalDifference,
        String form
){
}
