package com.plane.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi planesApi() {
        return GroupedOpenApi.builder()
                .group("Plane Service API")
                .pathsToMatch("/api/planes/**")
                .build();
    }

}
