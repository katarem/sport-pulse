package com.bytecodes.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ApiUser {
    private String username;
    private UUID userId;
}
