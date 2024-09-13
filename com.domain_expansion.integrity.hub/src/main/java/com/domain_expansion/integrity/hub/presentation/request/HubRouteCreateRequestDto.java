package com.domain_expansion.integrity.hub.presentation.request;


public record HubRouteCreateRequestDto(
        String startHubId,
        String endHubId,
        Integer duration,
        Integer distance
) {

}
