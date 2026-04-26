package com.bytecodes.response;

import com.bytecodes.model.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class ValidationResponse {
    private Boolean valid;
    private UUID userId;
    private String username;
    private UserRole role;
}
