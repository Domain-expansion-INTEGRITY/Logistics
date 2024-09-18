package com.domain_expansion.integrity.hub.presentation.response.hubRoute;

import com.domain_expansion.integrity.hub.domain.model.HubRoute;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HubRouteResponseDto(
        String hubRouteId,
        String startHubId,
        String endHubId,
        Integer duration,
        Integer distance
)
{

    public static HubRouteResponseDto of(HubRoute hubRoute) {
        return HubRouteResponseDto.builder()
                .hubRouteId(hubRoute.getHubRouteId())
                .startHubId(hubRoute.getStartHub().getHubId())
                .endHubId(hubRoute.getEndHub().getHubId())
                .duration(hubRoute.getDuration())
                .distance(hubRoute.getDistance())
                .build();
    }
}
