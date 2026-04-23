package com.bytecodes.dto.external;

import lombok.Data;

@Data
public class LeagueDTO {
    private Long id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Float season;
    private String round;
    private Boolean standings;
}
