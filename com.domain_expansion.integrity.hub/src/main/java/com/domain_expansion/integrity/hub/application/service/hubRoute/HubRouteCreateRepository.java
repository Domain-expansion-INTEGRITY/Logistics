package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.hubRoute.HubRouteResponseDto;

public interface HubRouteCreateRepository {

    HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto);
}
