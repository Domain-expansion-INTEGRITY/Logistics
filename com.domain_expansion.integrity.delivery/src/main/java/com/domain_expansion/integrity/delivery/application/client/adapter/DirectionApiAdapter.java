package com.domain_expansion.integrity.delivery.application.client.adapter;

import com.domain_expansion.integrity.delivery.application.client.DirectionApiClient;
import com.domain_expansion.integrity.delivery.application.client.config.DirectionApiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Configuration
@RequiredArgsConstructor
public class DirectionApiAdapter {

    private final DirectionApiConfig directionApiConfig;

    @Bean
    public DirectionApiClient weatherRestClient() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
                directionApiConfig.baseUrl());
        uriBuilderFactory.setEncodingMode(EncodingMode.NONE);

        RestClient restClient = RestClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // 여기서 모델을 만들어줘야 함
        return factory.createClient(DirectionApiClient.class);
    }
}
