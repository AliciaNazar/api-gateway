package com.mindhub.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouter(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("user-microservice",r -> r.path("/api/users/**")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("lb://user-microservice"))
                .route("product-microservice",r -> r.path("/api/products/**")
                        .uri("lb://product-microservice"))
                .route("order-microservice",r -> r.path("/api/orders/**")
                        .uri("lb://order-microservice"))
                .route("delete-product", r -> r.path("/api/products/{id}")
                        .and().method(HttpMethod.DELETE)
                        .and().header("Role", "ADMIN")
                        //.filters(f -> f.addRequestHeader("Authorization", "Bearer tokenDelAdmin"))  //aquí podría poner el token de admin cuando trabaje con eso
                        .uri("lb://product-microservice"))  // redirijo ahí
                .build();
    }
}

//Esto era para comprobar si se estaba añadiendo al header lo que yo quería
//                        .filters(f -> f
//                                .addRequestHeader("Hello", "World")
//                                .filter((exchange, chain) -> {
//                                    // Log the request headers
//                                    System.out.println("Request Headers: " + exchange.getRequest().getHeaders());
//                                    return chain.filter(exchange);
//                                }))