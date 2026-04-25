package com.bytecodes.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StadiumDTO {
    private String name;
    private String address;
    private String city;
    private int capacity;
    private String surface;
}
