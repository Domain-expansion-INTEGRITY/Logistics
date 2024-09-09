package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.application.mapper.UserMapper;
import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.repository.UserRepository;
import com.domain_expansion.integrity.user.domain.service.UserDomainService;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    // Domain
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // password encode
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 생성
     */
    @Transactional
    public Long createUser(UserCreateRequestDto requestDto) {

        User user = userMapper.createDtoToUser(requestDto);

        // 비밀번호 설정
        user.setPassword(passwordEncoder.encode(requestDto.password()));
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    /**
     * 유저 단일 조회
     */
    public UserResponseDto findUserById(Long userId) {
        User userInfo = findUserByIdAndCheck(userId);
        return UserResponseDto.from(userInfo);
    }

    /**
     * 유저 업데이트
     */
    @Override
    public UserResponseDto updateUserById(Long userId) {
        User userInfo = findUserByIdAndCheck(userId);
        return null;
    }

    /**
     * 유저 삭제
     */
    @Override
    public Long DeleteUserById(Long userId) {

        User userInfo = findUserByIdAndCheck(userId);
        userInfo.deleteEntity();

        return userInfo.getId();
    }

    /**
     * 유저 아이디가 존재하는 지 확인
     */
    private User findUserByIdAndCheck(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));
    }
}
