package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.HubRouteCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;

public interface HubRouteCreateRepository {

    HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto);
}
