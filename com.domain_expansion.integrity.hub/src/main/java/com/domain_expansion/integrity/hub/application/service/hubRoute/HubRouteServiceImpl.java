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
import com.domain_expansion.integrity.hub.presentation.response.HubRoutePaginatedResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.HubRouteTotalResponseDto;
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

    @Cacheable(cacheNames = "HubRouteAll",key = "'allRoute'+#page" )
    @Transactional(readOnly = true)
    @Override
    public HubRoutePaginatedResponseDto getHubRoutes(HubRouteSearchCondition searchDto, Pageable pageable) {

        Page<HubRouteResponseDto> pages = queryRepository.searchHubRoutes(searchDto,pageable);

        return HubRoutePaginatedResponseDto.of(pages);

    }

    @CacheEvict(cacheNames = "HubRouteAll", allEntries = true)
    @Override
    public HubRouteResponseDto updateHubRoute(HubRouteUpdateRequestDto requestDto, String hubRouteId) {

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

    @Override
    public HubRouteTotalResponseDto getRouteFromStartToEnd(String startHubId, String endHubId) {

        if(startHubId.equals(endHubId))
        {
            throw new HubException(ExceptionMessage.ROUTE_MUST_NOT_SAME);
        }

        Hub startHub = hubRepository.findById(startHubId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        Hub endHub = hubRepository.findById(endHubId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        boolean isForward = startHub.getIndex() < endHub.getIndex();

        List<Object[]> lists = null;

        if(isForward)
        {
            lists = hubRepository.findRouteInfoForward(startHubId,endHubId);
        }else{
            lists = hubRepository.findRouteInfoBackWard(startHubId,endHubId);
        }

        if(lists.isEmpty())
        {
            throw new HubException(ExceptionMessage.NOT_FOUND_HUB_ROUTE_ID);
        }

        HubRouteTotalResponseDto responseDto = null;

        for(Object[] objects : lists)
        {
            Integer total_duration = (Integer) objects[0];
            Integer total_distance = (Integer) objects[1];

            responseDto = new HubRouteTotalResponseDto(startHubId,endHubId,total_duration,total_distance);
        }

        return responseDto;
    }
}
