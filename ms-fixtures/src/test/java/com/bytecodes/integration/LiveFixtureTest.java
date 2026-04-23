package com.bytecodes.integration;

import com.bytecodes.config.CacheConfig;
import com.bytecodes.exception.GlobalExceptionHandler;
import com.bytecodes.mapper.FixtureMapperImpl;
import com.bytecodes.mapper.FixtureStatusMapperImpl;
import com.bytecodes.mapper.LeagueMapperImpl;
import com.bytecodes.mapper.TeamMapperImpl;
import com.bytecodes.mapper.VenueMapperImpl;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeStub;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({GlobalExceptionHandler.class, CacheConfig.class, LeagueMapperImpl.class, FixtureMapperImpl.class, VenueMapperImpl.class, TeamMapperImpl.class, FixtureStatusMapperImpl.class})
@AutoConfigureMockMvc
@WireMockTest(httpPort = 9000)
public class LiveFixtureTest {

    static final String ENDPOINT = "/api/fixtures/live";
    static final String EXTERNAL_ENDPOINT = "/fixtures";

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CacheManager cacheManager;

    @AfterEach
    void clear() {
        Optional.ofNullable(cacheManager.getCache("fixtures"))
                .ifPresent(Cache::clear);
    }

    @Test
    void get_live_fixtures_ok() throws Exception {

        Resource res = resourceLoader.getResource("classpath:__files/get_live/get-live-fixture-api-response.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());
        UUID stubId = UUID.randomUUID();

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("live", equalTo("all"))
                .withQueryParam("status", equalTo("1H-HT-2H"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_live/get-live-fixture-external-api-response.json")
                ).withId(stubId));

        mockMvc.perform(
            MockMvcRequestBuilders
                .get(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));


        removeStub(stubId);
    }

    @Test
    void get_live_fixtures_caches() throws Exception {

        Resource res = resourceLoader.getResource("classpath:__files/get_live/get-live-fixture-api-response.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        UUID stubId = UUID.randomUUID();

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("live", equalTo("all"))
                .withQueryParam("status", equalTo("1H-HT-2H"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_live/get-live-fixture-external-api-response.json")
                ).withId(stubId));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

       removeStub(stubId);

       Cache cache = cacheManager.getCache("fixtures");
       assertNotNull(cache);
       var cachedResult = cache.get(SimpleKey.EMPTY);
       assertNotNull(cachedResult);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));


    }

    @Test
    void get_fixtures_feign_api_error() throws Exception {

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("live", equalTo("all"))
                .withQueryParam("status", equalTo("1H-HT-2H"))
                .willReturn(
                        aResponse()
                                .withStatus(400)

                ));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("EXTERNAL_API_ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.EXTERNAL_API_CODE").value("400: Bad Request"));

    }

}
