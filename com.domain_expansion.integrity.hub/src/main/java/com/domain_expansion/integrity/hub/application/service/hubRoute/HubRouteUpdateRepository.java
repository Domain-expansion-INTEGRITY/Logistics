package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.presentation.request.HubRouteUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;

public interface HubRouteUpdateRepository {

    HubRouteResponseDto updateHubRoute(HubRouteUpdateRequestDto requestDto,String hubRouteId);
}
