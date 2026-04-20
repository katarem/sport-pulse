package com.bytecodes.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "api.football")
public class TeamProperties {
    @NotBlank
    private String key;

    @NotBlank
    private String url;


}
