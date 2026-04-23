package com.bytecodes.integration;

import com.bytecodes.config.CacheConfig;
import com.bytecodes.exception.GlobalExceptionHandler;
import com.bytecodes.mapper.EventPlayerMapperImpl;
import com.bytecodes.mapper.FixtureEventMapperImpl;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.Charset;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({GlobalExceptionHandler.class, CacheConfig.class, FixtureEventMapperImpl.class, EventPlayerMapperImpl.class})
@AutoConfigureMockMvc
@WireMockTest(httpPort = 9000)
public class FixtureEventsTest {

    static final String ENDPOINT = "/api/fixtures/{fixtureId}/events";
    static final String EXTERNAL_ENDPOINT = "/fixtures/events";

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
    void get_fixture_events_ok() throws Exception {

        long fixtureId = 1L;

        Resource res = resourceLoader.getResource("classpath:__files/get_events/ok.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("fixture", equalTo(Long.toString(fixtureId)))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_events/external-ok.json")
                ));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT, Long.toString(fixtureId))
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

    }

    @Test
    void get_fixtures_api_error() throws Exception {

        long fixtureId = 1L;

        Resource res = resourceLoader.getResource("classpath:__files/get_events/error.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("fixture", equalTo(Long.toString(fixtureId)))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_events/external-error.json")
                ));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT, Long.toString(fixtureId))
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

    }

    @Test
    void get_fixtures_caches() throws Exception {

        long fixtureId = 1L;

        Resource res = resourceLoader.getResource("classpath:__files/get_events/ok.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("fixture", equalTo(Long.toString(fixtureId)))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_events/external-ok.json")
                ));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT, Long.toString(fixtureId))
                                .contentType(MediaType.APPLICATION_JSON)

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

        Cache cache = cacheManager.getCache("fixtures");
        assertNotNull(cache);
        var savedCache = cache.get(fixtureId);
        assertNotNull(savedCache);
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT, Long.toString(fixtureId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

    }

    @Test
    void get_fixtures_feign_api_error() throws Exception {

        long fixtureId = 1L;

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("fixture", equalTo(Long.toString(fixtureId)))
                .willReturn(
                        aResponse()
                                .withStatus(400)
                ));

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT, Long.toString(fixtureId))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("EXTERNAL_API_ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.EXTERNAL_API_CODE").value("400: Bad Request"));

    }

}
