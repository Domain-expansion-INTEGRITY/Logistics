package com.domain_expansion.integrity.hub.application.service.deliveryman;

import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.HubDeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManCreateResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.HubDeliveryManCreateResposeDto;

public interface HubDeliveryManCreateService {

    DeliveryManCreateResponseDto createDeliveryMan(DeliveryManCreateRequestDto requestDto);

    HubDeliveryManCreateResposeDto createHubDeliveryMan(HubDeliveryManCreateRequestDto requestDto,String hubId, UserDetailsImpl userDetails);
}
