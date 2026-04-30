package com.bytecodes.client;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class StandingFilter {
    @NotNull(message = "El ID de la liga es obligatorio")
    @Min(value = 1,message = "El param League es obligatorio.")
    private Integer league;
    @NotNull(message = "El ID de la season es obligatorio")
    @Min(1)
    private Integer season;
}
