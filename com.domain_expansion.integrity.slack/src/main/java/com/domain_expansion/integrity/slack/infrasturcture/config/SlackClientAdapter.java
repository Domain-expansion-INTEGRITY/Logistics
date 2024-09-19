package com.domain_expansion.integrity.slack.infrasturcture.config;

import com.domain_expansion.integrity.slack.domain.webclient.SlackClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class SlackClientAdapter {

    private String slackUrl;

    public SlackClientAdapter(@Value("${slack.url}") String slackUrl) {
        this.slackUrl = slackUrl;
    }

    @Bean
    SlackClient slackWebClient() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(
            (slackUrl));

        RestClient restClient = RestClient.builder()
            .uriBuilderFactory(uriBuilderFactory)
            .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(SlackClient.class);
    }

}
