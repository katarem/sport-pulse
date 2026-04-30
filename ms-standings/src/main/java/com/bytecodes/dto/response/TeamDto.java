package com.bytecodes.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record TeamDto(Integer id, String name,
                      @JsonInclude(JsonInclude.Include.NON_NULL) String logo) { }
