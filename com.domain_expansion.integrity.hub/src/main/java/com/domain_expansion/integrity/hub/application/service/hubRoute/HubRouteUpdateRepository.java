package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRouteResponseDto;

public interface HubRouteUpdateRepository {

    HubRouteResponseDto updateHubRoute(HubRouteUpdateRequestDto requestDto,String hubRouteId);
}
