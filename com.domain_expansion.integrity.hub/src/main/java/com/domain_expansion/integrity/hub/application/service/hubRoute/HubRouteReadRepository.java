package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteReadRepository {

    HubRouteResponseDto getHubRoute(String hubRouteId);

    Page<HubRouteResponseDto> getHubRoutes(HubRouteSearchCondition searchDto,Pageable pageable);

}
