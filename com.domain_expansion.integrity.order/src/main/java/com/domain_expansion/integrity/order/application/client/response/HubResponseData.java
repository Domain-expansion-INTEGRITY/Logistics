package com.domain_expansion.integrity.order.application.client.response;

import java.util.List;
import lombok.Getter;

@Getter
public class HubResponseData extends CommonResponseData {

    private final HubResponseDto data;

    public HubResponseData(boolean success, String message, HubResponseDto data) {
        super(success, message);
        this.data = data;
    }

    public record HubResponseDto(
            String hubId,
            String name,
            String address,
            Double latitude,
            Double longitude,
            List<DeliverymanDto> deliverymanList
    ) {

        public record DeliverymanDto(
                String deliveryManId,
                String slackId
        ) {

        }
    }
}
