package com.domain_expansion.integrity.company.application.client;

import com.domain_expansion.integrity.company.application.client.response.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service")
public interface HubClient {

    @GetMapping("api/v1/hubs/{hub_id}")
    HubResponseDto findHubById(@PathVariable String hub_id);
}
