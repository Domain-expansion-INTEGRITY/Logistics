package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.HubRoutePaginatedResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteTotalResponseDto;
import org.springframework.data.domain.Pageable;

public interface HubRouteReadRepository {

    HubRouteResponseDto getHubRoute(String hubRouteId);

    HubRoutePaginatedResponseDto getHubRoutes(HubRouteSearchCondition searchDto,Pageable pageable);

    HubRouteTotalResponseDto getRouteFromStartToEnd(String startHubId, String endHubId);
}
