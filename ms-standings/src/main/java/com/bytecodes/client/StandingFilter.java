package com.bytecodes.client;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandingFilter {
    @Min(1)
    private Integer league;
    @Min(1)
    private Integer season;
}
