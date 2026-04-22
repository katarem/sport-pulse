package com.bytecodes.model;

import lombok.Data;

@Data
public class Team {
    private Long id;
    private String name;
    private String logo;
    private Integer goals;
}
