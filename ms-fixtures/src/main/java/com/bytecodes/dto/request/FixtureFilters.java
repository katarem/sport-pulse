package com.bytecodes.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class FixtureFilters {
    @Min(0)
    private Integer league;
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "La fecha solo puede darse en formato yyyy-MM-dd")
    private String date;
    private FixtureStatusValues status;
    // Aunque con la key gratuita solo podemos consultar entre 2022 y 2024 puede que se usen con una Key con más privilegios
    @Min(value = 1970, message = "El año mínimo es 1970")
    @Max(value = 2070, message = "El año máximo es 2070")
    private Integer season;
}
