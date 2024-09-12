package com.domain_expansion.integrity.hub.application.service;

import com.domain_expansion.integrity.hub.application.mapper.HubMapper;
import com.domain_expansion.integrity.hub.common.exception.HubException;
import com.domain_expansion.integrity.hub.common.message.ExceptionMessage;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.domain.repository.HubRepository;
import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.HubUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubCreateResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubDeliverManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubResponseDto;
import com.github.ksuid.Ksuid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    private final HubQueryRepository hubQueryRepository;

    /***
     * 허브 관리자만 가능
     * @param requestDto
     * @return
     */
    @Override
    public HubCreateResponseDto createHub(HubCreateRequestDto requestDto)
    {

        String ksuid = Ksuid.newKsuid().toString();

        Hub hub = hubMapper.HubCreateDtoToHub(requestDto,ksuid);

        Hub newHub = hubRepository.save(hub);

        return HubCreateResponseDto.from(newHub);

    }

    @Override
    public void deleteHub(String hubId,Long userId) {

        Hub hub = hubRepository.findById(hubId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        hub.deleteHub(userId);

    }

    @Transactional(readOnly = true)
    @Override
    public HubResponseDto getHubById(String hubId) {

        Hub hub = hubRepository.findById(hubId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        List<HubDeliverManResponseDto> deliveryManLists = hub.getDeliveryMans().stream()
                .map(delivery -> new HubDeliverManResponseDto(
                        delivery.getHubDeliveryManId(),
                        delivery.getDeliveryMan().getUserId()
                )).toList();

        return HubResponseDto.from(hub,deliveryManLists);
    }

    @Cacheable(cacheNames = "HubsAll",key = "'allHubs'")
    @Transactional(readOnly = true)
    @Override
    public Page<HubResponseDto> getAllHubs(HubSearchCondition searchCondition,Pageable pageable) {
        return hubQueryRepository.searchHubs(searchCondition,pageable);
    }

    @CacheEvict(value = "HubsAll", key = "'allHubs'")
    @Override
    public HubResponseDto updateHub(HubUpdateRequestDto requestDto, String hudId) {

        Hub hub = hubRepository.findById(hudId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        hub.updateWith(requestDto);

        return HubResponseDto.from(hub);
    }
}
