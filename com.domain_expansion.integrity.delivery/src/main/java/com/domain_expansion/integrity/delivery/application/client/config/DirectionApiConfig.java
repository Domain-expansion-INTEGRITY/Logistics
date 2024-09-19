package com.domain_expansion.integrity.delivery.application.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("direction")
public record DirectionApiConfig(
        String baseUrl,
        String key,
        String id
) {

}
