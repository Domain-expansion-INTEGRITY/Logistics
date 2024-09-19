package com.domain_expansion.integrity.delivery.application.client;

import com.domain_expansion.integrity.delivery.application.client.response.DirectionResponseDto;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface DirectionApiClient {

    @GetExchange
    DirectionResponseDto getDeliveryDirectionInfo(
            @RequestHeader("X-NCP-APIGW-API-KEY-ID") String clientId,
            @RequestHeader("X-NCP-APIGW-API-KEY") String secretKey,
            @RequestParam("start") String start,
            @RequestParam("goal") String goal
    );
}
