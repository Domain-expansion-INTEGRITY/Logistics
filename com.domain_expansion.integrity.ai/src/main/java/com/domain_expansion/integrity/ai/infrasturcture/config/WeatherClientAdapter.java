package com.domain_expansion.integrity.ai.infrasturcture.config;

import com.domain_expansion.integrity.ai.domain.model.weather.WeatherClient;
import com.domain_expansion.integrity.ai.domain.model.weather.WeatherConfig;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class WeatherClientAdapter {

    private final WeatherConfig weatherConfig;

    @Bean
    public WeatherClient weatherRestClient() {

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
            weatherConfig.baseurl());
        uriBuilderFactory.setEncodingMode(EncodingMode.NONE);

        RestClient restClient = RestClient.builder()
            .uriBuilderFactory(uriBuilderFactory)
            .requestInterceptor(logRequestInterceptor())
            .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        // 여기서 모델을 만들어줘야 함
        return factory.createClient(WeatherClient.class);
    }

    /**
     * RestClient에서 전송하는 url을 출력 확인 하는 interceptor
     */
    private ClientHttpRequestInterceptor logRequestInterceptor() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                ClientHttpRequestExecution execution) throws IOException {
                log.info("Request URL: {}", request.getURI()); // 출력 url 확인
                HttpHeaders headers = request.getHeaders();
                return execution.execute(request, body);
            }
        };
    }

}
