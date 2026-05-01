package com.bytecodes.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
            .servers(List.of(new Server().url("http://localhost:8080")))
            .info(new Info().title("Sport-Pulse Api Gateway").version("1.0.0").contact(new Contact().name("Equipo 2 - Andrés Diaz y Cris (Katarem)")));
    }

    
}
