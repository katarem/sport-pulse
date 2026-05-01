package com.bytecodes.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition
@SecurityScheme(
    name = "Internal Api Key",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER,
    paramName = "X-SPORTS-PULSE-API-TOKEN"
)
@SecurityScheme(
    name = "JWT",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
            .servers(List.of(new Server().url("http://localhost:8089")))
            .info(new Info()
            .title("Sport-Pulse Notifications")
            .description("Microservicio de notificaciones para Sports-Pulse")
            .version("1.0.0").contact(new Contact().name("Equipo 2 - Andrés Diaz y Cris (Katarem)")));
    }

    
}
