package com.bytecodes.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUser {
    @NotBlank
    @Pattern(regexp = "^\\S+$", message = "El nombre de usuario no puede estar vacío ni tener espacios")
    private String username;
    @Email
    @NotBlank
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "La contraseña debe tener mínimo 8 caracteres, al menos una mayúscula y un número.")
    private String password;
}
