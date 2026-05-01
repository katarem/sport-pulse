package com.bytecodes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiUser {
    private String serviceId;
    private String userToken;
}