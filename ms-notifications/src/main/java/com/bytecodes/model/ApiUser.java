package com.bytecodes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ApiUser {
    private String username;
    private UUID userId;
}
