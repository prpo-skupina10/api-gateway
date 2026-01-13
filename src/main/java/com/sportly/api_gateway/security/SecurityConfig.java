package com.sportly.api_gateway.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String SECRET =
        "VERY_SECRET_SUPER_DUPER_PASSWORD_123_PA_TUDI_NE_BO_SMELO_BITI_TUKAJ_V_KODI";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/api/auth/**").permitAll()
                .pathMatchers(
                    "/api/charts/**",
                    "/api/charts/docs",
                    "/api/charts/openapi.json",
                    "/api/charts/redoc",
                    "/api/charts/static/**"
                ).permitAll()
                .pathMatchers(
    "/actuator/health",
                    "/actuator/info"
                ).permitAll()
                .pathMatchers(
                    "/api/leagues/swagger-ui/**",
                    "/api/teams/swagger-ui/**",
                    "/api/players/swagger-ui/**",
                    "/api/import/swagger-ui/**",
                    "/api/users/swagger-ui/**",
                    "/api/import-betting/swagger-ui/**",
                    "/api/odds/swagger-ui/**",
                    "/api/betting/swagger-ui/**",

                    "/api/leagues/v3/api-docs/**",
                    "/api/teams/v3/api-docs/**",
                    "/api/players/v3/api-docs/**",
                    "/api/import/v3/api-docs/**",
                    "/api/users/v3/api-docs/**",
                    "/api/import-betting/v3/api-docs/**",
                    "/api/odds/v3/api-docs/**",
                    "/api/betting/v3/api-docs/**"
                ).permitAll()
                .pathMatchers(
                    "/api/users/health",
                    "/api/players/health",
                    "/api/teams/health",
                    "/api/leagues/health",
                    "/api/import/health",
                    "/api/import-betting/health",
                    "/api/odds/health",
                    "/api/betting/health",
                    "/health"
                ).permitAll()
                .pathMatchers(
                    "/api/odds/**",
                    "/api/betting/**",
                    "/api/import-betting/**"
                ).permitAll()
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}))
            .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withSecretKey(
            new SecretKeySpec(SECRET.getBytes(), "HmacSHA256")
        ).build();
    }
}