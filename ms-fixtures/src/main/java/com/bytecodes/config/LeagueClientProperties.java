package com.bytecodes.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "api.football")
@Data
@Validated
public class LeagueClientProperties {
    @NotBlank
    private String url;
    @NotBlank
    private String apiKey;
}
