package com.bytecodes.dto.external;

import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private String logo;
    private Boolean winner;
}
