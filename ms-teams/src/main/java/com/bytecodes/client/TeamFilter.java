package com.bytecodes.client;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@EqualsAndHashCode
public class TeamFilter {
    @NotNull(message = "El ID de la liga es obligatorio")
    @Min(value = 1, message = "Coloca el ID de la Liga. Ejm 61")
    private Integer league;
    @NotNull(message = "La temporada es obligatoria. Ejm 2024")
    @Range(min = 2022, max = 2024, message = "Sólo se puede las temporadas del 2022 al 2024.")
    private Integer season;
    @Min(value = 1,message = "Coloca el ID del equipo. Ejm 541")
    private Integer id;

    public static TeamFilter byId(Integer id) {
        TeamFilter t = new TeamFilter();
        t.setId(id);
        return t;
    }
}
