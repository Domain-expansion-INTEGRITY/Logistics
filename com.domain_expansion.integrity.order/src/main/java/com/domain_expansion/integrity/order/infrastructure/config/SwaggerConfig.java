package com.domain_expansion.integrity.order.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();

        addPageableSchemas(components); // Pageable 스키마 추가

        return new OpenAPI()
            .addServersItem(new Server().url("/"))
            .info(info())
            .components(components);

    }

    private void addPageableSchemas(Components components) {

        Schema<?> properties = new MapSchema()
            .properties(Map.of(
                "page", new IntegerSchema().example(1),
                "size", new IntegerSchema().example(10),
                "sort", new StringSchema().example("createdAt,desc")
            ));

        components.addSchemas("Pageable", properties);
    }

    private Info info() {
        return new Info()
            .title("무량공처 무결성")
            .version("0.0.1");
    }

}
