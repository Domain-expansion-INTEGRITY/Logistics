package com.domain_expansion.integrity.hub.application.mapper;

import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubAddress;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLatitude;
import com.domain_expansion.integrity.hub.domain.model.vo.hub.HubLongitude;
import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class HubMapper {

    public Hub HubCreateDtoToHub(HubCreateRequestDto requestDto,String hubId)
    {
        return Hub.from(
                hubId,
                requestDto.userId(),
                requestDto.name(),
                new HubAddress(requestDto.address()),
                new HubLatitude(requestDto.latitude()),
                new HubLongitude(requestDto.longitude())
        );
    }

}
