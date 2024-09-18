package com.domain_expansion.integrity.slack.application;

import com.domain_expansion.integrity.slack.application.client.UserClient;
import com.domain_expansion.integrity.slack.application.client.response.UserResponseDto;
import com.domain_expansion.integrity.slack.common.exception.SlackException;
import com.domain_expansion.integrity.slack.common.message.ExceptionMessage;
import com.domain_expansion.integrity.slack.common.response.SuccessResponse;
import com.domain_expansion.integrity.slack.domain.mapper.SlackMapper;
import com.domain_expansion.integrity.slack.domain.model.Slack;
import com.domain_expansion.integrity.slack.domain.repository.SlackRepository;
import com.domain_expansion.integrity.slack.domain.webclient.SlackClient;
import com.domain_expansion.integrity.slack.domain.webclient.SlackRequestDto;
import com.domain_expansion.integrity.slack.presentation.request.SlackCreateRequestDto;
import com.domain_expansion.integrity.slack.presentation.response.SlackResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SlackServiceImpl implements SlackService {

    private final SlackRepository slackRepository;
    private final SlackMapper slackMapper;

    private final SlackClient slackClient;
    private final UserClient userClient;

    /**
     * 슬랙 메세지 생성
     */
    @Override
    public SlackResponseDto createSlackMessage(SlackCreateRequestDto requestDto) {

        SuccessResponse<UserResponseDto> userInfoWithResponse = userClient.findUserById(
            requestDto.receivedId());

        String slackId = userInfoWithResponse.data().slackId();

        try {
            slackClient.sendSlackMessage(slackId, new SlackRequestDto(requestDto.message()));
        } catch (Exception e) {
            throw new SlackException(ExceptionMessage.AUTHORIZATION);
        }

        String ksUid = Ksuid.newKsuid().toString();

        Slack slack = slackMapper.createDtoToSlack(requestDto, ksUid);
        Slack savedSlack = slackRepository.save(slack);

        return SlackResponseDto.from(savedSlack);
    }

    @Override
    public SlackResponseDto findSlackById(String slackId) {
        Slack slack = slackRepository.findById(slackId)
            .orElseThrow(() -> new SlackException(ExceptionMessage.SLACK_NOT_FOUND));
        return SlackResponseDto.from(slack);
    }


    @Override
    public Page<SlackResponseDto> findSlackList(Pageable pageable) {
        Page<Slack> slackList = slackRepository.findAll(pageable);

        return slackList.map(SlackResponseDto::from);
    }
}
