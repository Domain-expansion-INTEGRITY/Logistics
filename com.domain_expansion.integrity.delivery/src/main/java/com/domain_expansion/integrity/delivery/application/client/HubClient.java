package com.domain_expansion.integrity.delivery.application.client;

import com.domain_expansion.integrity.delivery.application.client.response.DeliveryManResponseData;
import com.domain_expansion.integrity.delivery.application.client.response.HubResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "hub-service")
public interface HubClient {

    @GetMapping("/api/v1/hubs/deliveryMan/{deliveryManId}")
    ResponseEntity<DeliveryManResponseData> getDeliveryMan(@PathVariable("deliveryManId") String deliveryManId);

    @GetMapping("/api/v1/hubs/{hubId}")
    ResponseEntity<HubResponseData> getHub(@PathVariable("hubId") String hubId);

}
