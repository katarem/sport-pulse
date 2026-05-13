package com.bytecodes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class GatewayConfig {

    @Bean
    RouterFunction<ServerResponse> gatewayRoutes(
            Environment environment,
            HandlerFilterFunction<ServerResponse, ServerResponse> rateLimitFilter) {

        return route("auth")
                .GET("/api/auth/**", http())
                .POST("/api/auth/**", http())
                .before(uri(environment.getProperty("AUTH_SERVICE_URL", "http://localhost:8081")))
                .filter(rateLimitFilter)
                .build()

                .and(route("leagues")
                        .GET("/api/leagues/**", http())
                        .before(uri(environment.getProperty("LEAGUES_SERVICE_URL", "http://localhost:8082")))
                        .filter(rateLimitFilter)
                        .build())

                .and(route("teams")
                        .GET("/api/teams/**", http())
                        .before(uri(environment.getProperty("TEAMS_SERVICE_URL", "http://localhost:8083")))
                        .filter(rateLimitFilter)
                        .build())

                .and(route("fixtures")
                        .GET("/api/fixtures/**", http())
                        .before(uri(environment.getProperty("FIXTURES_SERVICE_URL", "http://localhost:8085")))
                        .filter(rateLimitFilter)
                        .build())

                .and(route("standings")
                        .GET("/api/standings/**", http())
                        .before(uri(environment.getProperty("STANDINGS_SERVICE_URL", "http://localhost:8086")))
                        .filter(rateLimitFilter)
                        .build())

                .and(route("notifications")
                        .GET("/api/notifications/**", http())
                        .POST("/api/notifications/**", http())
                        .DELETE("/api/notifications/**", http())
                        .before(uri(environment.getProperty("NOTIFICATIONS_SERVICE_URL", "http://localhost:8088")))
                        .filter(rateLimitFilter)
                        .build())

                .and(route("dashboard")
                        .GET("/api/dashboard/**", http())
                        .before(uri(environment.getProperty("DASHBOARD_SERVICE_URL", "http://localhost:8089")))
                        .filter(rateLimitFilter)
                        .build());
    }

}
