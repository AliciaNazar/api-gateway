package com.mindhub.api_gateway.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OpenApiCustomizer;



import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Gateway",
                version = "1.0",
                description = "Documentation of the API Gateway routes for the microservices"
        )
)
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer gatewayOpenApiCustomizer() {
        return openApi -> {
            Paths paths = new Paths();

            paths.addPathItem("/api/users/**", new PathItem()
                    .get(new Operation()
                            .tags(List.of("User Service"))
                            .summary("Redirection to the User Microservice")
                            .description("Routes to the user service.")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Success"))
                                    .addApiResponse("500", new ApiResponse().description("Internal server error")))));

            paths.addPathItem("/api/products/**", new PathItem()
                    .get(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Redirection to the Product Microservice")
                            .description("Routes to the product service.")
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse().description("Success"))
                                    .addApiResponse("500", new ApiResponse().description("Internal server error"))))
                    .delete(new Operation()
                            .tags(List.of("Product Service"))
                            .summary("Delete a product")
                            .description("Deletes a product through the product service. Only for ADMIN users.")
                            .responses(new ApiResponses()
                                    .addApiResponse("204", new ApiResponse().description("Product successfully deleted"))
                                    .addApiResponse("403", new ApiResponse().description("Forbidden"))
                                    .addApiResponse("404", new ApiResponse().description("Product not found"))
                                    .addApiResponse("500", new ApiResponse().description("Internal server error")))));

            paths.addPathItem("/api/orders/**", new PathItem()
                    .post(new Operation()
                            .tags(List.of("Order Service"))
                            .summary("Redirection to the Order Microservice")
                            .description("Routes to the order service.")
                            .responses(new ApiResponses()
                                    .addApiResponse("201", new ApiResponse().description("Order successfully created"))
                                    .addApiResponse("400", new ApiResponse().description("Bad request"))
                                    .addApiResponse("500", new ApiResponse().description("Internal server error")))));

            openApi.setPaths(paths);
        };
    }
}