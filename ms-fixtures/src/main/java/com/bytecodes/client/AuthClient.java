package com.bytecodes.client;

import com.bytecodes.config.AuthClientConfig;
import com.bytecodes.dto.external.ValidationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name="auth-client",
        url="${auth.url}",
        configuration=AuthClientConfig.class
)
public interface AuthClient {

    @PostMapping("/api/auth/validate")
    ValidationResponseDTO validateToken(@RequestHeader("Authorization") String token);

}
