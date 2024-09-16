package com.domain_expansion.integrity.hub.application.service.deliveryman;

import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManUpdateResponseDto;

public interface HubDeliveryManUpdateService {

    DeliveryManUpdateResponseDto updateDeliveryMan(DeliveryManUpdateRequestDto requestDto, String deliveryManId, UserDetailsImpl userDetails);
}
