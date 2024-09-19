package com.domain_expansion.integrity.delivery.application.service;

import com.domain_expansion.integrity.delivery.application.client.AiClient;
import com.domain_expansion.integrity.delivery.application.client.HubClient;
import com.domain_expansion.integrity.delivery.application.client.request.CompanyDeliverySequenceRequestDto;
import com.domain_expansion.integrity.delivery.application.client.response.AiResponseData.AiResponseDto;
import com.domain_expansion.integrity.delivery.application.client.response.AiResponseData.AiResponseDto.AiResponseAnswerDto;
import com.domain_expansion.integrity.delivery.application.client.response.DirectionResponseDto;
import com.domain_expansion.integrity.delivery.application.client.response.DirectionResponseDto.DirectionResponseDtoRoute.DirectionResponseDtoRouteTraoptimal;
import com.domain_expansion.integrity.delivery.application.client.response.HubResponseData.HubResponseDto;
import com.domain_expansion.integrity.delivery.common.exception.DeliveryException;
import com.domain_expansion.integrity.delivery.common.message.ExceptionMessage;
import com.domain_expansion.integrity.delivery.domain.model.Delivery;
import com.domain_expansion.integrity.delivery.domain.model.constant.DeliveryStatus;
import com.domain_expansion.integrity.delivery.domain.repository.DeliveryRepository;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryHistoryUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.SlackCreateRequestDto;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryHistoryScheduleService {

    private final DeliveryRepository deliveryRepository;
    private final AiClient aiClient;
    private final HubClient hubClient;
    private final DirectionService directionService;
    private final KafkaTemplate<String, SlackCreateRequestDto> slackKafkaTemplate;
    private final DeliveryService deliveryService;

    @Scheduled(cron = "0 0 6 * * *")
    @Transactional
    public void scheduleDeliveryHistory() {

        // 임시 쿼리
        List<Delivery> deliveries = deliveryRepository.findAll()
                .stream()
                .filter(o -> o.getStatus() == DeliveryStatus.WAITING_FOR_COMPANY)
                .toList();

        Map<String, Map<String, Map<String, List<Delivery>>>> groupedDeliveries = deliveries.stream()
                .filter(o -> o.getHubDeliveryManId() != null)
                .collect(Collectors.groupingBy(
                        Delivery::getEndHubId,
                        Collectors.groupingBy(
                                Delivery::getHubDeliveryManId,
                                Collectors.groupingBy(Delivery::getAddress)
                        )
                ));

        for (String endHubId : groupedDeliveries.keySet()) {
            Map<String, Map<String, List<Delivery>>> endHubGroupedDeliveries = groupedDeliveries.get(
                    endHubId);

            for (String hubDeliveryManId : endHubGroupedDeliveries.keySet()) {
                Map<String, List<Delivery>> addressGroupedDeliveries = endHubGroupedDeliveries.get(
                        hubDeliveryManId);

                List<Delivery> hubDeliveries = new ArrayList<>();

                for (String address : addressGroupedDeliveries.keySet()) {

                    hubDeliveries.addAll(addressGroupedDeliveries.get(address));
                }
                HubResponseDto hubResponseDto = hubClient.getHub(endHubId).getBody().getData();

                CompanyDeliverySequenceRequestDto requestDto = CompanyDeliverySequenceRequestDto.of(
                        hubDeliveries, hubResponseDto);

                AiResponseDto aiResponseDto = aiClient.getCompanyDeliverySequence(requestDto)
                        .getBody().getData();

                AiResponseAnswerDto start = aiResponseDto.answer().get(0);
                String startName = URLEncoder.encode(start.starting(), StandardCharsets.UTF_8);
                Double startLongitude = hubResponseDto.longitude();
                Double startLatitude = hubResponseDto.latitude();

                StringBuilder startSb = new StringBuilder();
                startSb.append(startLongitude).append(",").append(startLatitude)
                        .append(",name=").append(startName);

                StringBuilder endSb = new StringBuilder();
                for (int i = 0; i < aiResponseDto.answer().size(); i++) {
                    AiResponseAnswerDto aiResponseAnswerDto = aiResponseDto.answer().get(i);
                    Double longitude = aiResponseAnswerDto.longitude();
                    Double latitude = aiResponseAnswerDto.latitude();

                    String name = URLEncoder.encode(aiResponseAnswerDto.ending(),
                            StandardCharsets.UTF_8);

                    endSb.append(longitude).append(",").append(latitude).append(",name=")
                            .append(name).append(":");
                }

                DirectionResponseDto deliveryDirectionInfo = directionService.getDeliveryDirectionInfo(
                        startSb.toString(), endSb.toString());

                for (int i = 0; i < deliveryDirectionInfo.route().traoptimal().size(); i++) {
                    DirectionResponseDtoRouteTraoptimal traoptimal = deliveryDirectionInfo.route()
                            .traoptimal().get(i);

                    String address = aiResponseDto.answer().get(i+1).starting();

                    Delivery delivery = hubDeliveries.stream()
                            .filter(o -> o.getAddress().equals(address)).findFirst()
                            .orElseThrow(
                                    () -> new DeliveryException(ExceptionMessage.NOT_FOUND_DELIVERY)
                            );
                    DeliveryHistoryUpdateRequestDto deliveryHistoryUpdateRequestDto = new DeliveryHistoryUpdateRequestDto(
                            traoptimal.summary().distance(),
                            traoptimal.summary().duration()
                    );
                    deliveryService.updateDeliveryHistory(deliveryHistoryUpdateRequestDto,
                            delivery.getDeliveryId());
                }

                StringBuilder slackSb = new StringBuilder();
                slackSb.append("★ 오늘의 배송 정보입니다. ★\n");
                Integer totalDistance = 0;
                Integer totalDuration = 0;
                for (int i = 0; i < deliveryDirectionInfo.route().traoptimal().size(); i++) {
                    DirectionResponseDtoRouteTraoptimal traoptimal = deliveryDirectionInfo.route()
                            .traoptimal().get(i);

                    String startAddress = aiResponseDto.answer().get(i).starting();
                    String goalAddress = aiResponseDto.answer().get(i).ending();
                    Integer exDistance = traoptimal.summary().distance();
                    Integer exDuration = traoptimal.summary().duration();
                    totalDistance += exDistance;
                    totalDuration += exDuration;

                    slackSb.append("출발 주소: ").append(startAddress).append(" 도착 주소: ").append(goalAddress).append("\n");
                    slackSb.append("예상 시간: ").append(TimeUnit.MILLISECONDS.toMinutes(exDuration)).append(" 분\n");
                    slackSb.append("예상 거리: ").append(exDistance).append(" meters\n");
                }

                slackSb.append("======================================================\n");
                slackSb.append("총 예상 시간: ").append(TimeUnit.MILLISECONDS.toMinutes(totalDuration)).append(" 분\n");
                slackSb.append("총 예상 거리: ").append(totalDistance).append(" meters\n");

                SlackCreateRequestDto slackCreateRequestDto = new SlackCreateRequestDto(
                        hubDeliveryManId, slackSb.toString());

                slackKafkaTemplate.send("ai.slack", slackCreateRequestDto);
            }
        }
    }
}

