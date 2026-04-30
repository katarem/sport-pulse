package com.bytecodes.dto.external;

import lombok.Data;

import java.util.UUID;

@Data
public class ValidationResponseDTO {
    private boolean valid;
    private String username;
    private String role;
    private UUID userId;
}
