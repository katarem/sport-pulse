package com.bytecodes.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserToken {
    private String token;
    private String tokenType;
    private Long expiresIn;
    private UUID userId;
}
