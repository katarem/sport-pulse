package com.bytecodes.integration;

import com.bytecodes.config.GatewayConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(GatewayConfig.class)
@TestPropertySource(properties = {
        "ratelimit.capacity=300",
        "ratelimit.refillAmount=300"
})
@AutoConfigureMockMvc
public class GatewayTest {

    @Autowired
    MockMvc mockMvc;

    static Map<String, WireMockServer> routes = Map.of(
            "/api/auth/hello", new WireMockServer(wireMockConfig().port(8081)),
            "/api/leagues/hello", new WireMockServer(wireMockConfig().port(8082)),
            "/api/teams/hello", new WireMockServer(wireMockConfig().port(8083)),
            "/api/fixtures/hello", new WireMockServer(wireMockConfig().port(8085)),
            "/api/standings/hello", new WireMockServer(wireMockConfig().port(8086)),
            "/api/notifications/hello", new WireMockServer(wireMockConfig().port(8088)),
            "/api/dashboard/hello", new WireMockServer(wireMockConfig().port(8089))
    );

    @BeforeAll
    static void setup() {
        for(Map.Entry<String, WireMockServer> route : routes.entrySet()) {
            route.getValue()
                    .addStubMapping(get(route.getKey()).willReturn(ok()).build());
            route.getValue().start();
        }
    }

    @AfterAll
    static void tearUp() {
        for(Map.Entry<String, WireMockServer> route : routes.entrySet()) {
            route.getValue().stop();
        }
    }

    @Test
    void gateway_service_works_for_any_registered_endpoint() throws Exception {
        for (String route : routes.keySet()) {
            mockMvc.perform(MockMvcRequestBuilders.get(route))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void gateway_service_does_not_work_on_other_endpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin"))
                .andExpect(status().isNotFound());
    }

}
