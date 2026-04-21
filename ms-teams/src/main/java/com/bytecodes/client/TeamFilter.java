package com.bytecodes.client;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamFilter {
    @Min(value = 1, message = "Coloca el ID de la Liga. Ejm 61")
    private int league;
    @Min(value = 1,message = "Coloca el año de la temporada. Ejm 2024")
    private int season;
}
