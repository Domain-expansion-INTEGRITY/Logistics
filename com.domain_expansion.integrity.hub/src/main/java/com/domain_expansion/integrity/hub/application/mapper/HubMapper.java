package com.domain_expansion.integrity.hub.application.mapper;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.HubRoute;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.HubRouteCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class HubMapper {

    public Hub HubCreateDtoToHub(HubCreateRequestDto requestDto,String hubId)
    {
        return Hub.from(
                hubId,
                requestDto.userId(),
                requestDto.name(),
                requestDto.address(),
                requestDto.index(),
                new HubLatitude(requestDto.latitude()),
                new HubLongitude(requestDto.longitude())
        );
    }

    public HubRoute HubRouteCreateDtoToHubRoute(Hub startHub,Hub endHub,Integer duration,Integer distance,String hubRouteId)
    {
        return HubRoute.from(
                hubRouteId,
                startHub,
                endHub,
                duration,
                distance
        );
    }

}
