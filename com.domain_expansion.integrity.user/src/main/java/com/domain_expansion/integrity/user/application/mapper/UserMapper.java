package com.domain_expansion.integrity.user.application.mapper;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * 생성 Dto to User mapper
     */
    public User createDtoToUser(UserCreateRequestDto requestDto) {
        return User.from(requestDto.username(), requestDto.role(), requestDto.phoneNumber(),
            requestDto.slackId());
    }

}
