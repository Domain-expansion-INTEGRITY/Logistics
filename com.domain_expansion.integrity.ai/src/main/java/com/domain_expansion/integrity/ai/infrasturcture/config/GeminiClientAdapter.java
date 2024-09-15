package com.domain_expansion.integrity.ai.infrasturcture.config;

import com.domain_expansion.integrity.ai.domain.model.gemini.GeminiClient;
import com.domain_expansion.integrity.ai.domain.model.gemini.GeminiProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RequiredArgsConstructor
@Configuration
public class GeminiClientAdapter {

    private final GeminiProperty geminiProperty;

    @Bean
    public GeminiClient geminiRestClient() {
        RestClient restClient = RestClient.builder().baseUrl(geminiProperty.baseurl()).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // 여기서 모델을 만들어줘야 함
        return factory.createClient(GeminiClient.class);
    }
}
