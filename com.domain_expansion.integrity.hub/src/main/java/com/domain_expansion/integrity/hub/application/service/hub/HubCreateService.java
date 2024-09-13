package com.domain_expansion.integrity.hub.application.service.hub;

import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubCreateResponseDto;

public interface HubCreateService {

    HubCreateResponseDto createHub(HubCreateRequestDto requestDto);

}
