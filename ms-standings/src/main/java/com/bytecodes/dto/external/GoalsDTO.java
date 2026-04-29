package com.bytecodes.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoalsDTO(@JsonProperty("for") Integer goalsFor, Integer against) {
}
