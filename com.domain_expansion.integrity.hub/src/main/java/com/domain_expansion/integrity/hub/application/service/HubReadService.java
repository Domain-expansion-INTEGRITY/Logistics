package com.domain_expansion.integrity.hub.application.service;

import com.domain_expansion.integrity.hub.presentation.request.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubReadService {

    HubResponseDto getHubById(String hubId);

    Page<HubResponseDto> getAllHubs(HubSearchCondition searchCondition,Pageable pageable);

}
