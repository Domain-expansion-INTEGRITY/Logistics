package com.domain_expansion.integrity.hub.application.service;

import com.domain_expansion.integrity.hub.application.mapper.HubMapper;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.repository.HubRepository;
import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.HubUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubCreateResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl implements HubService{

    private final HubRepository hubRepository;

    private final HubMapper hubMapper;

    @Override
    public HubCreateResponseDto createHub(HubCreateRequestDto requestDto)
    {

        String ksuid = Ksuid.newKsuid().toString();

        Hub hub = hubMapper.HubCreateDtoToHub(requestDto,ksuid);

        Hub newHub = hubRepository.save(hub);

        return HubCreateResponseDto.from(newHub);

    }

    @Override
    public void deleteHub(String hubId) {

    }

    @Override
    public HubResponseDto getHubById(String hubId) {
        return null;
    }

    @Override
    public Page<HubResponseDto> getAllHubs(Pageable pageable) {
        return null;
    }

    @Override
    public HubResponseDto updateHub(HubUpdateRequestDto requestDto) {
        return null;
    }
}
