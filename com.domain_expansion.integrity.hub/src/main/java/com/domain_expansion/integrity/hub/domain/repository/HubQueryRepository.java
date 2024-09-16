package com.domain_expansion.integrity.hub.domain.repository;

import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.hub.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRouteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HubQueryRepository {

    Page<HubResponseDto> searchHubs(HubSearchCondition condition, Pageable pageable);

    Page<HubRouteResponseDto> searchHubRoutes(HubRouteSearchCondition condition, Pageable pageable);

    Page<DeliveryManResponseDto> searchDeliveryMans(DeliveryManSearchCondition condition, Pageable pageable,
            UserDetailsImpl userDetail);

}
