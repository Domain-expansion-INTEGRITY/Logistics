package com.domain_expansion.integrity.hub.application.service.hub;

import com.domain_expansion.integrity.hub.presentation.request.hub.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubPaginatedResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubValidateResponseDto;
import org.springframework.data.domain.Pageable;

public interface HubReadService {

    HubResponseDto getHubById(String hubId);

    HubPaginatedResponseDto getAllHubs(HubSearchCondition searchCondition,Pageable pageable);

    HubValidateResponseDto validateUserInHub(String hubId, Long userId);

}
