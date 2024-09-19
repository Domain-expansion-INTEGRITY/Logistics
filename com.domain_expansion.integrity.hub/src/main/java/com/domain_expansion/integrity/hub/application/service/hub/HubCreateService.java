package com.domain_expansion.integrity.hub.application.service.hub;

import com.domain_expansion.integrity.hub.presentation.request.hub.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubCreateResponseDto;

public interface HubCreateService {

    HubCreateResponseDto createHub(HubCreateRequestDto requestDto);

}
