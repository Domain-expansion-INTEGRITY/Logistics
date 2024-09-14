package com.domain_expansion.integrity.hub.domain.repository;

import com.domain_expansion.integrity.hub.presentation.request.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HubQueryRepository {

    Page<HubResponseDto> searchHubs(HubSearchCondition condition, Pageable pageable);

    Page<HubRouteResponseDto> searchHubRoutes(HubRouteSearchCondition condition, Pageable pageable);
}
