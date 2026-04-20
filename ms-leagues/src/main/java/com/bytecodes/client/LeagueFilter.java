package com.bytecodes.client;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class LeagueFilter {

    @Size(min = 4, message = "Minimo cuatro caracteres y en inglés, Ej. Spain")
    private String country;

    @Range(min=2022, max=2024, message = "Sólo se puede las temporadas del 2022 al 2024")
    private Integer season;

    @Min(1)
    private Integer id;

    public static LeagueFilter of(String country, Integer season) {
        LeagueFilter f = new LeagueFilter();
        f.setCountry(country);
        f.setSeason(season);
        return f;
    }

    public static LeagueFilter byId(Integer id) {
        LeagueFilter f = new LeagueFilter();
        f.setId(id);
        return f;
    }
}
