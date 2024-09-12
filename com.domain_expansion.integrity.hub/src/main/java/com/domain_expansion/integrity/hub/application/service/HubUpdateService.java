package com.domain_expansion.integrity.hub.application.service;

import com.domain_expansion.integrity.hub.presentation.request.HubUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;

public interface HubUpdateService {
    HubResponseDto updateHub(HubUpdateRequestDto requestDto,String hubId);
}
