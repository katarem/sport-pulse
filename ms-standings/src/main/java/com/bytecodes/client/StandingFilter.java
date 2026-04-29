package com.bytecodes.client;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class StandingFilter {

    @Min(value = 1,message = "El param League es obligatorio.")
    private Integer league;
    @Min(1)
    private Integer season;
}
