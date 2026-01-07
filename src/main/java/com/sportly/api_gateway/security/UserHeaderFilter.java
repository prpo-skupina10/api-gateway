package com.sportly.api_gateway.security;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserHeaderFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return exchange.getPrincipal()
            .cast(JwtAuthenticationToken.class)
            .map(auth -> {
                var jwt = auth.getToken();
                return exchange.getRequest().mutate()
                    .header("X-User-Id", jwt.getClaimAsString("userId"))
                    .header("X-Username", jwt.getSubject())
                    .build();
            })
            .defaultIfEmpty(exchange.getRequest())
            .flatMap(req ->
                chain.filter(exchange.mutate().request(req).build())
            );
    }
}