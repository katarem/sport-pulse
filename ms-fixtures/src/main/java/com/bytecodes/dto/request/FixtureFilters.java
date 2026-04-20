package com.bytecodes.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FixtureFilters {
    @Min(0)
    @NotNull
    private Integer league;
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$")
    private String date;
    private FixtureStatusValues status;
    @Min(1970)
    @Max(9999)
    @NotNull
    private Integer season;
}
