package com.bytecodes.integration;

import com.bytecodes.config.CacheConfig;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.exception.GlobalExceptionHandler;
import com.bytecodes.mapper.FixtureMapperImpl;
import com.bytecodes.mapper.FixtureStatusMapperImpl;
import com.bytecodes.mapper.TeamMapperImpl;
import com.bytecodes.mapper.VenueMapperImpl;
import com.bytecodes.util.DateUtil;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({GlobalExceptionHandler.class, CacheConfig.class, FixtureMapperImpl.class, VenueMapperImpl.class, TeamMapperImpl.class, FixtureStatusMapperImpl.class})
@AutoConfigureMockMvc
public class FixtureTest {

    static final WireMockServer externalApi = new WireMockServer(wireMockConfig().port(9000));

    static final String ENDPOINT = "/api/fixtures";
    static final String EXTERNAL_ENDPOINT = "/fixtures";

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CacheManager cacheManager;

    final ZonedDateTime date = ZonedDateTime.of(2026, 4, 18, 10, 0, 0, 0, ZoneId.systemDefault());

    @BeforeAll
    static void setUp() {
        externalApi.start();
    }

    @AfterAll
    static void tearDown() {
        externalApi.stop();
    }

    @Test
    void get_fixtures_ok() throws Exception {

        var filters = new FixtureFilters();
        filters.setDate(DateUtil.getSimpleDate(date));
        filters.setLeague(1);
        filters.setSeason(1970);

        Resource res = resourceLoader.getResource("classpath:__files/get-fixture-api-response.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        externalApi.addStubMapping(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                        .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date)))
                        .withQueryParam("league", equalTo("1"))
                        .withQueryParam("season", equalTo("1970"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                        .withBodyFile("get-fixture-external-api-response.json")
                        )
                        .build()
        );

        mockMvc.perform(
            MockMvcRequestBuilders
                .get(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParam("date", DateUtil.getSimpleDate(date))
                    .queryParam("league", filters.getLeague().toString())
                    .queryParam("season", filters.getSeason().toString())
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

    }

    @Test
    void get_fixtures_api_error() throws Exception {

        var filters = new FixtureFilters();
        filters.setDate(DateUtil.getSimpleDate(date.plusDays(1)));
        filters.setLeague(1);
        filters.setSeason(1970);

        Resource res = resourceLoader.getResource("classpath:__files/get-fixture-api-error.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        externalApi.addStubMapping(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                        .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date.plusDays(1))))
                        .withQueryParam("league", equalTo("1"))
                        .withQueryParam("season", equalTo("1970"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                        .withBodyFile("get-fixture-external-api-error.json")
                        )
                        .build()
        );

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("date", DateUtil.getSimpleDate(date.plusDays(1)))
                                .queryParam("league", filters.getLeague().toString())
                                .queryParam("season", filters.getSeason().toString())
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

    }

    @Test
    void get_fixtures_caches() throws Exception {
        var filters = new FixtureFilters();
        filters.setDate(DateUtil.getSimpleDate(date));
        filters.setLeague(1);
        filters.setSeason(1970);

        Resource res = resourceLoader.getResource("classpath:__files/get-fixture-api-response.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        externalApi.addStubMapping(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                        .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date)))
                        .withQueryParam("league", equalTo("1"))
                        .withQueryParam("season", equalTo("1970"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                        .withBodyFile("get-fixture-external-api-response.json")
                        )
                        .build()
        );

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("date", DateUtil.getSimpleDate(date))
                                .queryParam("league", filters.getLeague().toString())
                                .queryParam("season", filters.getSeason().toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

        // eliminamos el stubmapping no deberia llamar a la api
        externalApi.removeStubMapping(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date)))
                .withQueryParam("league", equalTo("1"))
                .withQueryParam("season", equalTo("1970"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get-fixture-external-api-response.json")
                )
                .build());

        Optional<Cache> cachedResult = Optional.ofNullable(cacheManager.getCache(String.format("{%s, %s}", DateUtil.getSimpleDate(date), filters)));
        assertTrue(cachedResult.isPresent());

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("date", DateUtil.getSimpleDate(date))
                                .queryParam("league", filters.getLeague().toString())
                                .queryParam("season", filters.getSeason().toString())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedJson));

    }

    @Test
    void get_fixtures_feign_api_error() throws Exception {

        var filters = new FixtureFilters();
        filters.setDate(DateUtil.getSimpleDate(date.plusDays(2)));

        externalApi.addStubMapping(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                        .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date.plusDays(2))))
                        .willReturn(
                                aResponse()
                                        .withStatus(400)
                        )
                        .build()
        );

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(ENDPOINT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .queryParam("date", DateUtil.getSimpleDate(date.plusDays(2)))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("EXTERNAL_API_ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors.EXTERNAL_API_CODE").value("400: Bad Request"));

    }

}
