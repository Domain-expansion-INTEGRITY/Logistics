package com.domain_expansion.integrity.hub.application.service.deliveryman;

import com.domain_expansion.integrity.hub.application.client.UserClient;
import com.domain_expansion.integrity.hub.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.hub.application.mapper.HubMapper;
import com.domain_expansion.integrity.hub.application.shared.RoleConstants;
import com.domain_expansion.integrity.hub.common.exception.HubException;
import com.domain_expansion.integrity.hub.common.message.ExceptionMessage;
import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.domain.model.DeliveryMan;
import com.domain_expansion.integrity.hub.domain.model.DeliveryState;
import com.domain_expansion.integrity.hub.domain.model.Hub;
import com.domain_expansion.integrity.hub.domain.model.HubDeliveryMan;
import com.domain_expansion.integrity.hub.domain.repository.DeliveryManRepository;
import com.domain_expansion.integrity.hub.domain.repository.HubQueryRepository;
import com.domain_expansion.integrity.hub.domain.repository.HubRepository;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.HubDeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.HubDeliveryRequsetDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManCreateResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManNextResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.DeliveryManUpdateResponseDto;
import com.domain_expansion.integrity.hub.presentation.response.deliveryMan.HubDeliveryManCreateResposeDto;
import com.github.ksuid.Ksuid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class HubDeliveryManServiceImpl implements HubDeliveryManService {

    private final HubRepository hubRepository;
    private final HubQueryRepository queryRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final HubMapper hubMapper;
    private final UserClient userClient;
    private final RedisTemplate<String,String> redisTemplate;

    @Override
    public DeliveryManCreateResponseDto createDeliveryMan(DeliveryManCreateRequestDto requestDto) {

        UserResponseDto userDto = userClient.findByUserId(requestDto.userId());

        if(deliveryManRepository.findByUserId(requestDto.userId()).isPresent()){
            throw new HubException(ExceptionMessage.ALREADY_EXISTED_USER);
        }

        String deliveryManId = Ksuid.newKsuid().toString();

        DeliveryMan createDeliveryMan = hubMapper.DeliveryManCreateDtoToDeliveryMan(requestDto, deliveryManId);

        return DeliveryManCreateResponseDto.from(deliveryManRepository.save(createDeliveryMan));
    }

    @Override
    public HubDeliveryManCreateResposeDto createHubDeliveryMan(
            HubDeliveryManCreateRequestDto requestDto,String hubId,UserDetailsImpl userDetails) {

        String role = userDetails.getRole();
        Long userId = userDetails.getUserId();

        //소속된 허브가 존재하는 지
        Hub hub = hubRepository.findById(hubId).orElseThrow(
                ()-> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
        );

        List<String> lists = requestDto.deliveryManIds().stream().map(HubDeliveryRequsetDto::deliveryManId).toList();

        List<DeliveryMan> results = deliveryManRepository.findAllByDeliveryManIdIn(lists);

        if(results.size() != requestDto.deliveryManIds().size())
        {
            throw new HubException(ExceptionMessage.RE_CHECK_DELIVERY_MAN_ID);
        }

        if(RoleConstants.ROLE_HUB_MANAGER.equals(role))
        {
            //현재의 허브의 매니저인지 확인
            hubRepository.findByHubIdAndUserId(hubId,userId).orElseThrow(
                    () -> new HubException(ExceptionMessage.DONT_HAVE_AUTHORIZED)
            );

        }

        // 배송 담당자들이 현재 허브에 소속된 배송 담당자가 아닌지 검증
        List<String> deliveryManIds = lists;
        List<String> existingDeliveryManIds = hub.getDeliveryMans().stream()
                .map(hubDeliveryMan -> hubDeliveryMan.getDeliveryMan().getDeliveryManId())
                .toList();

        deliveryManIds.stream()
                .filter(existingDeliveryManIds::contains)
                .findAny()
                .ifPresent(id -> {
                    throw new HubException(ExceptionMessage.DELIVERY_MAN_ALREADY_EXISTS);
                });

        // 새로운 HubDeliveryMan 생성 및 저장
        List<HubDeliveryMan> hubDeliveryMans= results.stream().map(
                deliveryMan -> {
                    String HubDeliveryMan = Ksuid.newKsuid().toString();
                    return hubMapper.HubDeliveryManCreateDtoToHubDeliveryMan(hub,deliveryMan,HubDeliveryMan);
                }
        ).toList();

        hubDeliveryMans.forEach(hub::addDeliveryMan);

        hubRepository.save(hub);

        return HubDeliveryManCreateResposeDto.of(lists);
    }

    @Override
    public DeliveryManUpdateResponseDto updateDeliveryMan(DeliveryManUpdateRequestDto requestDto,
            String deliveryManId, UserDetailsImpl userDetails) {

        userClient.findByUserId(requestDto.userId());

        DeliveryMan deliveryMan = deliveryManRepository.findByDeliveryManId(deliveryManId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_DELIVERY_MAN)
        );

        String role = userDetails.getRole();
        Long userId = userDetails.getUserId();
        //본인 허브에 소속된 배송 담당자만 관리 가능
        isMatchHubManager(deliveryManId, role, userId);

        deliveryMan.updateWith(requestDto);

        return DeliveryManUpdateResponseDto.from(deliveryMan);
    }

    @Override
    public void deleteDeliveryMan(String deliveryManId, UserDetailsImpl userDetails) {

        DeliveryMan deliveryMan = deliveryManRepository.findByDeliveryManId(deliveryManId).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_DELIVERY_MAN)
        );

        String role = userDetails.getRole();
        Long userId = userDetails.getUserId();
        //본인 허브에 소속된 배송 담당자만 관리 가능
        isMatchHubManager(deliveryManId, role, userId);

        deliveryMan.deleteDeliveryMan(userId);

    }

    private void isMatchHubManager(String deliveryManId, String role, Long userId) {

        if(RoleConstants.ROLE_HUB_MANAGER.equals(role)){

            Hub hub = hubRepository.findHubByUserId(userId).orElseThrow(
                    () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
            );

            boolean existed = hub.getDeliveryMans().stream().anyMatch(delivery -> delivery.getDeliveryMan().getDeliveryManId().equals(
                    deliveryManId));

            if(!existed){
                throw new HubException(ExceptionMessage.DONT_HAVE_AUTHORIZED);
            }
        }
    }

    @Transactional(readOnly = true)
    @Override
    public DeliveryManResponseDto getDeliveryManById(String id,UserDetailsImpl userDetails) {

        DeliveryMan man = deliveryManRepository.findByDeliveryManId(id).orElseThrow(
                () -> new HubException(ExceptionMessage.NOT_FOUND_DELIVERY_MAN)
        );

        String role = userDetails.getRole();
        Long userId = userDetails.getUserId();

        if(RoleConstants.ROLE_HUB_MANAGER.equals(role)){

            Hub hub = hubRepository.findHubByUserId(userId).orElseThrow(
                    () -> new HubException(ExceptionMessage.NOT_FOUND_HUB_ID)
            );

            boolean existed = hub.getDeliveryMans().stream().anyMatch(delivery -> delivery.getDeliveryMan().getDeliveryManId().equals(
                    id));

            if(!existed){
                throw new HubException(ExceptionMessage.DONT_HAVE_AUTHORIZED);
            }

            return DeliveryManResponseDto.from(man.getDeliveryManId(),man.getUserId(),hub.getHubId());
        }

        return DeliveryManResponseDto.from(man.getDeliveryManId(), man.getUserId());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<DeliveryManResponseDto> getAllDeliveryMans(DeliveryManSearchCondition searchDto,
            Pageable pageable,UserDetailsImpl userDetails) {
        return queryRepository.searchDeliveryMans(searchDto,pageable,userDetails);
    }

    /***
     * Redis를 이용하여 실시간 처리 & ROUND ROBIN
     * @return
     */
    @Transactional
    @Override
    public DeliveryManNextResponseDto findNextDeliveryMan() {

        //Redis에서 마지막 인덱스 가져오기
        String lastKsuid = redisTemplate.opsForValue().get("lastDeliveryManId");

        //배달 가능한 허브 배송담당자들을 조회
        List<DeliveryMan> deliveryManList = queryRepository.findAllDeliveryMans();

        if (deliveryManList.isEmpty()) {
            throw new HubException(ExceptionMessage.NOT_FOUND_DELIVERY_MAN);
        }

        DeliveryMan nextDeliveryMan = null;

        //처음 시작하면 1번째로 설정
        if (lastKsuid == null) {
            nextDeliveryMan = deliveryManList.get(0);
            lastKsuid = nextDeliveryMan.getDeliveryManId();
        }else{
            final String lastDeliveryManId = lastKsuid;

            nextDeliveryMan = deliveryManList.stream().filter(dm -> dm.getDeliveryManId().compareTo(lastDeliveryManId) > 0)
                    .findFirst().orElse(deliveryManList.get(0));

        }

        nextDeliveryMan.updatedState(DeliveryState.RUNNING);

        redisTemplate.opsForValue().set("lastDeliveryManId", nextDeliveryMan.getDeliveryManId());

        return DeliveryManNextResponseDto.from(nextDeliveryMan);
    }

}
