package com.bytecodes.integration;

import com.bytecodes.config.CacheConfig;
import com.bytecodes.dto.request.FixtureFilters;
import com.bytecodes.exception.GlobalExceptionHandler;
import com.bytecodes.mapper.FixtureMapperImpl;
import com.bytecodes.mapper.FixtureStatusMapperImpl;
import com.bytecodes.mapper.LeagueMapperImpl;
import com.bytecodes.mapper.TeamMapperImpl;
import com.bytecodes.mapper.VenueMapperImpl;
import com.bytecodes.util.DateUtil;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({GlobalExceptionHandler.class, CacheConfig.class, LeagueMapperImpl.class, FixtureMapperImpl.class, VenueMapperImpl.class, TeamMapperImpl.class, FixtureStatusMapperImpl.class})
@AutoConfigureMockMvc
@WireMockTest(httpPort = 9000)
public class FixtureTest {

    static final String ENDPOINT = "/api/fixtures";
    static final String EXTERNAL_ENDPOINT = "/fixtures";

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CacheManager cacheManager;

    final ZonedDateTime date = ZonedDateTime.of(2026, 4, 18, 10, 0, 0, 0, ZoneId.systemDefault());

    @AfterEach
    void clear() {
        Optional.ofNullable(cacheManager.getCache("fixtures"))
                .ifPresent(Cache::clear);
    }

    @Test
    void get_fixtures_ok() throws Exception {

        var filters = new FixtureFilters();
        filters.setDate(DateUtil.getSimpleDate(date));
        filters.setLeague(1);
        filters.setSeason(1970);

        Resource res = resourceLoader.getResource("classpath:__files/get_all/get-fixture-api-response.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date)))
                .withQueryParam("league", equalTo("1"))
                .withQueryParam("season", equalTo("1970"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_all/get-fixture-external-api-response.json")
                ));

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

        Resource res = resourceLoader.getResource("classpath:__files/get_all/get-fixture-api-error.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        stubFor(get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date.plusDays(1))))
                .withQueryParam("league", equalTo("1"))
                .withQueryParam("season", equalTo("1970"))
                .willReturn(
                        aResponse()
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                .withBodyFile("get_all/get-fixture-external-api-error.json")
                )
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

        Resource res = resourceLoader.getResource("classpath:__files/get_all/get-fixture-api-response.json");
        String expectedJson = res.getContentAsString(Charset.defaultCharset());

        stubFor(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                        .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date)))
                        .withQueryParam("league", equalTo("1"))
                        .withQueryParam("season", equalTo("1970"))
                        .willReturn(
                                aResponse()
                                        .withHeader("Content-Type", MediaType.APPLICATION_JSON.toString())
                                        .withBodyFile("get_all/get-fixture-external-api-response.json")
                        )
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

        Cache cache = cacheManager.getCache("fixtures");
        assertNotNull(cache);
        var savedCache = cache.get(List.of(filters.getDate(), filters));
        assertNotNull(savedCache);
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

        stubFor(
                get(urlPathEqualTo(EXTERNAL_ENDPOINT))
                        .withQueryParam("date", equalTo(DateUtil.getSimpleDate(date.plusDays(2))))
                        .willReturn(
                                aResponse()
                                        .withStatus(400)
                        )
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
