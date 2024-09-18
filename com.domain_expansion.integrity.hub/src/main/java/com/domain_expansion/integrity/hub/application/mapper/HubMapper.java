package com.domain_expansion.integrity.hub.application.mapper;

import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.HubDeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.HubRoute;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.HubDeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.hub.HubCreateRequestDto;
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

    public DeliveryMan DeliveryManCreateDtoToDeliveryMan(DeliveryManCreateRequestDto requestDto,String deliveryManId){

        return DeliveryMan.from(
                deliveryManId,
                requestDto.userId()
        );
    }

    public HubDeliveryMan HubDeliveryManCreateDtoToHubDeliveryMan(
            Hub hub ,DeliveryMan deliveryMan,String HubDeliveryManId){

        return HubDeliveryMan.from(
                HubDeliveryManId,
                hub,
                deliveryMan
        );
    }

}
