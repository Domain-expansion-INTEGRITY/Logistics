package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.application.client.DirectionApiClient;
import com.domain_expansion.integrity.delivery.application.client.config.DirectionApiConfig;
import com.domain_expansion.integrity.delivery.application.client.response.DirectionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectionService {

    private final DirectionApiConfig directionApiConfig;
    private final DirectionApiClient directionApiClient;

    public DirectionResponseDto getDeliveryDirectionInfo(String start, String goal) {

        DirectionResponseDto deliveryDirectionInfo = directionApiClient.getDeliveryDirectionInfo(
                directionApiConfig.id(), directionApiConfig.key(), start, goal);

        return deliveryDirectionInfo;
    }
}
