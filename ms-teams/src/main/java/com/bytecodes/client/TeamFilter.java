package com.bytecodes.client;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamFilter {
    @NotBlank
    @Min(1)
    private int league;
    @NotBlank
    @Min(1)
    private int season;
}
