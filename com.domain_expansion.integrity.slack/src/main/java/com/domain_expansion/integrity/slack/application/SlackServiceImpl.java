package com.domain_expansion.integrity.slack.application;

import com.domain_expansion.integrity.slack.domain.mapper.SlackMapper;
import com.domain_expansion.integrity.slack.domain.model.Slack;
import com.domain_expansion.integrity.slack.domain.repository.SlackRepository;
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

    /**
     * 슬랙 메세지 생성
     */
    @Override
    public SlackResponseDto createSlackMessage(SlackCreateRequestDto requestDto) {

        // TODO: 유저 정보 FeignClient로 전달받는다

        // TODO: 슬랙 전달

        String ksUid = Ksuid.newKsuid().toString();

        Slack slack = slackMapper.createDtoToSlack(requestDto, ksUid);
        Slack savedSlack = slackRepository.save(slack);

        return SlackResponseDto.from(savedSlack);
    }

    @Override
    public SlackResponseDto findSlackById(String slackId) {
        return null;
    }


    @Override
    public Page<SlackResponseDto> findSlackList(Pageable pageable) {
        Page<Slack> slackList = slackRepository.findAll(pageable);

        return slackList.map(SlackResponseDto::from);
    }
}
