package com.bytecodes.entity;

import com.bytecodes.model.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class ValidationUser {
    private Boolean valid;
    private UUID userId;
    private String username;
    private UserRole role;
}
