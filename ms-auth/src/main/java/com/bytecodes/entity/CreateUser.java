package com.bytecodes.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUser {
    @NotBlank
    @Pattern(regexp = "^\\S+$")
    private String username;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;
}
