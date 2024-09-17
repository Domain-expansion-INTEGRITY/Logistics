package com.domain_expansion.integrity.ai.domain.model.gemini;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gemini")
public record GeminiProperty(
    String baseurl,
    String key
) {

}
