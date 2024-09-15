package com.domain_expansion.integrity.ai.application.aiHistory;

import com.domain_expansion.integrity.ai.application.mapper.AiMapper;
import com.domain_expansion.integrity.ai.common.exception.AiException;
import com.domain_expansion.integrity.ai.common.message.ExceptionMessage;
import com.domain_expansion.integrity.ai.common.utils.TranslateGeoToXY;
import com.domain_expansion.integrity.ai.common.utils.TranslateGeoToXY.LatXLngY;
import com.domain_expansion.integrity.ai.domain.model.AiHistory;
import com.domain_expansion.integrity.ai.domain.model.AiPrompt;
import com.domain_expansion.integrity.ai.domain.model.PromptType;
import com.domain_expansion.integrity.ai.domain.model.weather.WeatherService;
import com.domain_expansion.integrity.ai.domain.repository.aihistory.AiRepository;
import com.domain_expansion.integrity.ai.domain.service.AiDomainService;
import com.domain_expansion.integrity.ai.presentation.request.AiCompanyDeliverRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.AiDeliverySequenceRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.AiHubDeliveryRequestDto;
import com.domain_expansion.integrity.ai.presentation.request.DeliveryInfo;
import com.domain_expansion.integrity.ai.presentation.response.AiDeliverySlackResponseDto;
import com.domain_expansion.integrity.ai.presentation.response.AiResponseDto;
import com.domain_expansion.integrity.ai.presentation.response.CompanyDeliveryInfo;
import com.domain_expansion.integrity.ai.presentation.response.CompanyDeliveryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ksuid.Ksuid;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AiServiceImpl implements AiService {

    private final AiRepository aiRepository;
    private final AiDomainService aiDomainService;
    private final WeatherService weatherService;

    private final AiMapper aiMapper;
    private final ObjectMapper objectMapper;


    /**
     * 업체로 배송 담당자를 위한 Slack Message
     */
    @Transactional
    @Override
    public AiDeliverySlackResponseDto createCompanyDeliverySlackMessage(
        AiCompanyDeliverRequestDto requestDto) {

        AiPrompt aiPrompt = aiDomainService.findByTypeAndCheck(PromptType.DELIVER_WITH_WEATHER);

        // 위도 경도 -> X Y
        LatXLngY latXLngY = TranslateGeoToXY.convertGRID_GPS(TranslateGeoToXY.TO_GRID,
            requestDto.latitude(),
            requestDto.longitude());

//        log.info("출력 x y 좌표 : {}", latXLngY);

        // 변환한 x y 좌표를 갖고 날씨 정보를 얻어온다.
        String weatherInfoADay = weatherService.getDailyWeatherInfo(
            String.valueOf((int) latXLngY.x),
            String.valueOf((int) latXLngY.y),
            requestDto.hubId()
        );

        // 배송 정보 List를 하나의 String으로 만든다.
        String deliveryJoin = deliveryListToString(requestDto.deliveryList());

        String question = "배송 지역의 날씨 : " + weatherInfoADay + "\n"
            + "배송 정보 :" + deliveryJoin
            + "\n" + aiPrompt.getPrompt();

        // DB는 날씨정보 축소해서 저장
        String shortQuestionForDB = "배송 지역의 날씨 : " + weatherInfoADay.substring(0, 200) + "\n"
            + "배송 정보 :" + deliveryJoin
            + "\n" + aiPrompt.getPrompt();

        // aiPrompt로 부터 question을 생성해낸다.
        AiHistory ai = aiMapper.createDtoToAiHistory(
            shortQuestionForDB, Ksuid.newKsuid().toString(), aiPrompt
        );

        AiHistory savedAi = aiRepository.save(ai);

        // GEMINI AI 호출해서 답을 갖고 온다.
        String geminiAnswer = aiDomainService.generateAiAnswer(question,
            requestDto.hubId() + "-" + aiPrompt.getName().name());
        savedAi.updateAnswer(geminiAnswer);

        return AiDeliverySlackResponseDto.from(savedAi, geminiAnswer, requestDto.userId());
    }


    /**
     * 허브 배송 담당자를 위한 슬랙 메세지
     */
    @Override
    public AiDeliverySlackResponseDto createHubDeliverySlackMessage(
        AiHubDeliveryRequestDto requestDto) {

        AiPrompt aiPrompt = aiDomainService.findByTypeAndCheck(PromptType.HUB_DELIVERY_MESSAGE);

        // 배송 정보 List를 하나의 String으로 만든다.
        String deliveryJoin = deliveryListToString(requestDto.deliveryList());

        String question = "출발 배송지 : " + requestDto.hubAddress() + "\n"
            + "배송 정보 :" + deliveryJoin
            + "\n" + aiPrompt.getPrompt();

        // aiPrompt로 부터 question을 생성해낸다.
        AiHistory ai = aiMapper.createDtoToAiHistory(
            question, Ksuid.newKsuid().toString(), aiPrompt
        );

        // 호출 기록을 남기기 위해 호출 전에 저장을 한다.
        AiHistory savedAi = aiRepository.save(ai);

        // GEMINI AI 호출해서 답을 갖고 온다.
        String geminiAnswer = aiDomainService.generateAiAnswer(question,
            requestDto.hubId() + "-" + aiPrompt.getName().name());
        savedAi.updateAnswer(geminiAnswer);

        return AiDeliverySlackResponseDto.from(savedAi, geminiAnswer, requestDto.userId());
    }

    /**
     * 허브에서 업체로 보내는 순서 지정
     */
    @Transactional
    public CompanyDeliveryResponseDto createCompanyDeliveryPath(
        AiDeliverySequenceRequestDto requestDto) {

        AiPrompt aiPrompt = aiDomainService.findByTypeAndCheck(PromptType.DELIVERY_SEQUENCE);

        // 배송 정보 List를 하나의 String으로 만든다.
        String deliveryJoin = deliveryListToString(requestDto.deliveryList());

        // 보내기 전에 생성
        StringBuilder sb = new StringBuilder();
        sb.append("출발 허브 주소 : ").append(requestDto.hubAddress()).append("\n");
        sb.append("배송 예정 주소 : ").append(deliveryJoin).append("\n");
        sb.append(aiPrompt.getPrompt());
        String question = sb.toString();

        // 보내기 전에 저장
        AiHistory ai = aiMapper
            .createDtoToAiHistory(question, Ksuid.newKsuid().toString(), aiPrompt);
        AiHistory savedAi = aiRepository.save(ai);

        // GEMINI AI 호출해서 답을 갖고 온다.
        String geminiAnswer = aiDomainService.generateAiAnswer(question,
            requestDto.hubId() + "-" + aiPrompt.getName().name());

        // 업데이트
        savedAi.updateAnswer(geminiAnswer);

        // String to Response
        List<CompanyDeliveryInfo> dtoList = parsingCompanyDeliverSequence(geminiAnswer);

        // 리턴

        return CompanyDeliveryResponseDto.from(savedAi, dtoList, requestDto.userId());
    }

    /**
     * AI History 단일 조회
     */
    @Override
    public AiResponseDto findAiHistoryById(String aiId) {
        AiHistory aiHistory = aiRepository.findById(aiId)
            .orElseThrow(() -> new AiException(ExceptionMessage.INVALID_HISTORY_ID));

        return AiResponseDto.from(aiHistory);
    }

    /**
     * Ai History 목록 조회
     */
    @Override
    public Page<AiResponseDto> findAiHistoryList(Pageable pageable, PromptType type) {

        // 현재 타입만 받으므로 만약 조건이 많아지면 QueryDSL로 변경
        Page<AiHistory> aiHistoryList;
        if (type == null) {
            aiHistoryList = aiRepository.findAll(pageable);

        } else {
            aiHistoryList = aiRepository.findAllByAiPrompt_Name(type, pageable);
        }

        return aiHistoryList.map(AiResponseDto::from);
    }


    /**
     * ====================================================================================================
     * 아래는 Private 함수
     * ====================================================================================================
     */

    // gemini에 맞춰서 파싱
    private List<CompanyDeliveryInfo> parsingCompanyDeliverSequence(String text) {
//        text = "```json\n[\n  {\n    \"starting\": \"경복궁\",\n    \"ending\": \"서울시청\",\n    \"latitude\": 37.5666805,\n    \"longitude\": 126.9782912\n  },\n  {\n    \"starting\": \"서울시청\",\n    \"ending\": \"강남역\",\n    \"latitude\": 37.498095,\n    \"longitude\": 127.027570\n  },\n  {\n    \"starting\": \"강남역\",\n    \"ending\": \"광명역\",\n    \"latitude\": 37.454506,\n    \"longitude\": 126.869385\n  }\n]\n``` \n";
        String replaceText = text.replace("```json\n", "").replace("```", "").trim();

        try {
            return Arrays.asList(
                objectMapper.readValue(replaceText, CompanyDeliveryInfo[].class)
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AiException(ExceptionMessage.INVALID_PROMPT_TYPE);
        }
    }

    /**
     * 배송 List String으로 변환
     */
    private String deliveryListToString(List<DeliveryInfo> requestDto) {
        return String.join(",", requestDto.stream()
            .map(DeliveryInfo::address).toList());
    }

}
