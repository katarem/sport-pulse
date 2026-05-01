package com.bytecodes.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "api")
public class TokenProperties {

    private List<String> allowedServices;

}
