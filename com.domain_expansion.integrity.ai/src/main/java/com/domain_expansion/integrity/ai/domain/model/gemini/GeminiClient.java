package com.domain_expansion.integrity.ai.domain.model.gemini;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface GeminiClient {

    @PostExchange()
    GeminiResponse findAllByQuery(
        @RequestParam("key") String apiKey,
        @RequestBody GeminiRequest requestBody
    );


}
