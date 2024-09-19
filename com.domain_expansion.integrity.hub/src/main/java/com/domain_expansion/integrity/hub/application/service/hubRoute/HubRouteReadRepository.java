package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRoutePaginatedResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRouteResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRouteTotalResponseDto;
import org.springframework.data.domain.Pageable;

public interface HubRouteReadRepository {

    HubRouteResponseDto getHubRoute(String hubRouteId);

    HubRoutePaginatedResponseDto getHubRoutes(HubRouteSearchCondition searchDto,Pageable pageable);

    HubRouteTotalResponseDto getRouteFromStartToEnd(String startHubId, String endHubId);
}
