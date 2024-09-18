package com.domain_expansion.integrity.hub.application.service.hub;

import com.domain_expansion.integrity.hub.application.mapper.HubMapper;
import com.domain_expansion.integrity.hub.common.Serializer.EventSerializer;
import com.domain_expansion.integrity.hub.common.exception.HubException;
import com.domain_expansion.integrity.hub.common.message.ExceptionMessage;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.domain.repository.HubRepository;
import com.domain_expansion.integrity.hub.events.HubDeleteEvent;
import com.domain_expansion.integrity.hub.presentation.request.hub.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.hub.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.hub.HubUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubCreateResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubDeliverManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubPaginatedResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.hub.HubValidateResponseDto;
import com.github.ksuid.Ksuid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HubServiceImpl implements HubService{

    private final HubRepository hubRepository;

    private final HubMapper hubMapper;

    private final HubQueryRepository hubQueryRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

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

        //허브 삭제 이벤트 발행
        HubDeleteEvent event = new HubDeleteEvent(hub.getHubId());
        kafkaTemplate.send("hub-deleted-topic", EventSerializer.serialize(event));

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

    @Cacheable(cacheNames = "HubsAll",key = "'allHubs'+#page" )
    @Transactional(readOnly = true)
    @Override
    public HubPaginatedResponseDto getAllHubs(HubSearchCondition searchCondition,Pageable pageable) {
        Page<HubResponseDto> pages = hubQueryRepository.searchHubs(searchCondition,pageable);

        return HubPaginatedResponseDto.of(pages);
    }

    @CacheEvict(value = "HubsAll", allEntries = true)
    @Override
    public HubResponseDto updateHub(HubUpdateRequestDto requestDto, String hudId) {

        Hub hub = hubRepository.findById(hudId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        hub.updateWith(requestDto);

        return HubResponseDto.from(hub);
    }

    @Transactional(readOnly = true)
    @Override
    public HubValidateResponseDto validateUserInHub(String hubId, Long userId) {

        boolean result = hubRepository.findByHubIdAndUserId(hubId,userId).isPresent();

        return new HubValidateResponseDto(result);

    }

}
