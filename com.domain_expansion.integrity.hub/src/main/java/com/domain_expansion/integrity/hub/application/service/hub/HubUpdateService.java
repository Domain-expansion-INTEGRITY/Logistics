package com.domain_expansion.integrity.hub.application.service.hub;

import com.domain_expansion.integrity.hub.presentation.request.hub.HubUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubResponseDto;

public interface HubUpdateService {
    HubResponseDto updateHub(HubUpdateRequestDto requestDto,String hubId);
}
