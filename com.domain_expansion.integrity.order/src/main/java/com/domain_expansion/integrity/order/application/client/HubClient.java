package com.domain_expansion.integrity.order.application.client;

import com.domain_expansion.integrity.order.application.client.response.HubResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "hub-service")
public interface HubClient {

    @GetMapping("/api/v1/hubs/user/{userId}")
    ResponseEntity<HubResponseData> getHubByUserId(@PathVariable("userId") Long userId);
}
