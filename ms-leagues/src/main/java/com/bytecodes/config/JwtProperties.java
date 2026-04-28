package com.bytecodes.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotBlank(message = "Se debe configurar la secret key")
    private String secretKey;

    @Min(value = 0, message = "La expiración no puede ser menor a 0")
    private long expiration;

}
