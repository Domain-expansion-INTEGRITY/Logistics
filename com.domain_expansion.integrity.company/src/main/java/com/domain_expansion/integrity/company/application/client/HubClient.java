package com.domain_expansion.integrity.company.application.client;

import com.domain_expansion.integrity.company.application.client.config.FeignConfig;
import com.domain_expansion.integrity.company.application.client.response.HubResponseDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "hub-service",url = "http://localhost:19093",  configuration = FeignConfig.class)
public interface HubClient {

    @GetMapping("/api/v1/hubs/{hub_id}")
    HubResponseDto findHubById(@PathVariable String hub_id);

    @GetMapping("/api/v1/hubs/users/{user_id}")
    HubResponseDto findHubByUserId(@PathVariable Long user_id);

}
