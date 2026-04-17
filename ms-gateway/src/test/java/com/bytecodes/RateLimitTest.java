package com.bytecodes;

import com.bytecodes.config.GatewayConfig;
import com.bytecodes.config.RateLimitConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.TimeUnit;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({GatewayConfig.class, RateLimitConfig.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RateLimitTest {

    static final String GATEWAY_URL = "/api/teams";

    @Autowired
    MockMvc client;

    static WireMockServer wireMock = new WireMockServer(wireMockConfig().port(8083));

    String ip = "0.0.0.0";

    @BeforeAll
    static void beforeAll() {
        wireMock.start();
    }

    @AfterAll
    static void afterAll() {
        wireMock.stop();
    }

    @Test
    void first_rate_limit_should_work() throws Exception {

        ip = "1.1.1.1";

        wireMock.stubFor(get(GATEWAY_URL).willReturn(ok().withBody("[]")));

        for (int i = 0; i < 5; i++) {
            client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
                    .andExpect(status().isOk());
        }
    }

    @Test
    void request_after_rate_limit_should_fail() throws Exception {

        ip = "2.2.2.2";

        wireMock.stubFor(get(GATEWAY_URL).willReturn(ok().withBody("[]")));

        for (int i = 0; i < 5; i++) {
            client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
                    .andExpect(status().isOk());
        }

        client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
            .andExpect(status().isTooManyRequests());

    }

    @Test
    void request_after_waiting_rate_limit_should_success() throws Exception {

        ip = "3.3.3.3";

        wireMock.stubFor(get(GATEWAY_URL).willReturn(ok().withBody("[]")));

        client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
                .andExpect(status().isOk());

        for (int i = 0; i < 4; i++) {
            client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
                    .andExpect(status().isOk());
        }

        // next fails which is normal
        client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
                .andExpect(status().isTooManyRequests());

        // when time's up we should be able to request again
        Thread.sleep(1000L);

        client.perform(MockMvcRequestBuilders.get(GATEWAY_URL).remoteAddress(ip))
                .andExpect(status().isOk());

    }

}
