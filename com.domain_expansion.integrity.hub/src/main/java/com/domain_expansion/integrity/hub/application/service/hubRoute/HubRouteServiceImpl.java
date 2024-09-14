package com.domain_expansion.integrity.hub.application.service.hubRoute;

import com.domain_expansion.integrity.hub.application.mapper.HubMapper;
import com.domain_expansion.integrity.hub.common.exception.HubException;
import com.domain_expansion.integrity.hub.common.message.ExceptionMessage;
import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.HubRoute;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.domain.repository.HubRepository;
import com.domain_expansion.integrity.hub.presentation.request.HubRouteCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.HubRouteUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HubRouteServiceImpl implements HubRouteService {

    private final HubRepository hubRepository;
    private final HubQueryRepository queryRepository;
    private final HubMapper hubMapper;

    @Override
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {

        String ksuid = Ksuid.newKsuid().toString();

        Hub startHub = hubRepository.findById(requestDto.startHubId()).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        Hub endHub = hubRepository.findById(requestDto.endHubId()).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        HubRoute hubRoute = hubMapper.HubRouteCreateDtoToHubRoute(startHub,endHub,
                requestDto.duration(), requestDto.distance(),ksuid);

        startHub.getStartRoutes().add(hubRoute);
        endHub.getEndRoutes().add(hubRoute);

        hubRepository.save(startHub);
        hubRepository.save(endHub);

        return HubRouteResponseDto.of(hubRoute);
    }

    @Override
    public void deleteHubRoute(String hubRouteId, UserDetailsImpl userDetails) {

        //1. 상위 객체인 허브 가져오기
        Hub existedHub = hubRepository.findByStartRoutes_HubRouteId(hubRouteId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        //2. 하위 객체인 허브이동정보 가져오기
        HubRoute hubRoute = existedHub.getStartRoutes().stream()
                .filter(route -> route.getHubRouteId().equals(hubRouteId))
                .findFirst()
                .orElseThrow(() -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ROUTE_ID));

        //3. 논리적 삭제
        hubRoute.deleteHubRoute(userDetails.getUserId());

        //하위 정보와 함께 저장
        hubRepository.save(existedHub);
    }

    @Transactional(readOnly = true)
    @Override
    public HubRouteResponseDto getHubRoute(String hubRouteId) {

        Hub existedHub = hubRepository.findByStartRoutes_HubRouteId(hubRouteId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        HubRoute hubRoute = existedHub.getStartRoutes().stream()
                .filter(route -> route.getHubRouteId().equals(hubRouteId))
                .findFirst()
                .orElseThrow(() -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ROUTE_ID));

        return HubRouteResponseDto.of(hubRoute);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<HubRouteResponseDto> getHubRoutes(HubRouteSearchCondition searchDto, Pageable pageable) {
        return queryRepository.searchHubRoutes(searchDto,pageable);
    }

    @Override
    public HubRouteResponseDto updateHubRoute(HubRouteUpdateRequestDto requestDto, String hubRouteId) {
        //hubRouteId를 이용하여 startRoute를 가진 Hub를 가져오기
        Hub existedHub = hubRepository.findByStartRoutes_HubRouteId(hubRouteId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        HubRoute hubRoute = existedHub.getStartRoutes().stream()
                .filter(route -> route.getHubRouteId().equals(hubRouteId))
                .findFirst()
                .orElseThrow(() -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ROUTE_ID));

        hubRoute.updateHubRoute(requestDto);

        hubRepository.save(existedHub);

        return HubRouteResponseDto.of(hubRoute);

    }

}
