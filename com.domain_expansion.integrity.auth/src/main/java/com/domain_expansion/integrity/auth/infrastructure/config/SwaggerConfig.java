package com.domain_expansion.integrity.auth.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();

        return new OpenAPI()
            .addServersItem(new Server().url("/"))
            .info(info())
            .components(components);
    }

    private Info info() {
        return new Info()
            .title("무량공처 무결성")
            .version("0.0.1");
    }

}
