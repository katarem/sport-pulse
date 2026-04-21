package com.bytecodes.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TeamResponseDTO {

    private int id;
    private String name;
    private String country;
    private String logo;
    private int founded;
    private Boolean national;
    private StadiumDTO stadium;
}
