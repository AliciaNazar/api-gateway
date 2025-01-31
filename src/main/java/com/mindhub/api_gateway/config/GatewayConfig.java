package com.mindhub.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AdminFilter adminFilter;

    @Bean
    public RouteLocator customRouter(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("user-microservice", r -> r.path("/api/auth/**")
                        .uri("lb://user-microservice"))
                .route("user-microservice",r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://user-microservice"))
                .route("user-microservice",r -> r.path("/api/admin/**")
                        .filters(f -> f.filters(jwtAuthenticationFilter,adminFilter))
                        .uri("lb://user-microservice"))
                .route("product-microservice",r -> r.path("/api/products/public/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://product-microservice"))
                .route("product-microservice",r -> r.path("/api/products/admin/**")
                        .filters(f -> f.filters(jwtAuthenticationFilter,adminFilter))
                        .uri("lb://product-microservice"))
                .route("order-microservice",r -> r.path("/api/orders/user/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://order-microservice"))
                .route("order-microservice",r -> r.path("/api/orders/admin/**")
                        .filters(f -> f.filters(jwtAuthenticationFilter,adminFilter))
                        .uri("lb://order-microservice"))
                .route("orderItem-service", r->r.path("/api/order-items/user/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://order-service"))
                .build();
    }
}
