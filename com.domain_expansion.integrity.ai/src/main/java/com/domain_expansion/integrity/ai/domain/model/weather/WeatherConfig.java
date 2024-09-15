package com.domain_expansion.integrity.ai.domain.model.weather;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("weather")
public record WeatherConfig(
    String baseurl,
    String key
) {

}