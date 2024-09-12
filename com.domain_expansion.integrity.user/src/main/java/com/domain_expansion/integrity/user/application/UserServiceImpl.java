package com.domain_expansion.integrity.user.application;

import com.domain_expansion.integrity.user.application.mapper.UserMapper;
import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.repository.UserQueryRepository;
import com.domain_expansion.integrity.user.domain.repository.UserRepository;
import com.domain_expansion.integrity.user.domain.service.UserDomainService;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.request.UserLoginRequestDto;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import com.domain_expansion.integrity.user.presentation.request.UserUpdateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    // Domain
    private final UserDomainService userDomainService;

    // Repo
    private final UserRepository userRepository;
    private final UserQueryRepository userQueryRepository;

    private final UserMapper userMapper;

    // password encode
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 생성
     */
    @Override
    @Transactional
    public Long createUser(UserCreateRequestDto requestDto) {

        User user = userMapper.createDtoToUser(requestDto);

        // 비밀번호 설정
        user.updatePassword(passwordEncoder.encode(requestDto.password()));
        User savedUser = userRepository.save(user);

        return savedUser.getId();
    }

    /**
     * 유저 단일 조회
     */
    @Override
    public UserResponseDto findUserById(Long userId) {
        User userInfo = findUserByIdAndCheck(userId);
        return UserResponseDto.from(userInfo);
    }

    /**
     * 유저 목록 조회
     */
    @Override
    public Page<UserResponseDto> findUserList(Pageable pageable,
        
        UserSearchCondition searchCondition) {
        Page<User> userList = userQueryRepository.findAllUserByCondition(pageable, searchCondition);

        return userList.map(UserResponseDto::from);
    }

    /**
     * 유저 업데이트
     */
    @Transactional
    @Override
    public UserResponseDto updateUserById(Long userId, UserUpdateRequestDto requestDto) {
        User userInfo = findUserByIdAndCheck(userId);

        List<User> checkUserList = userDomainService.checkPhoneNumber(userId,
            requestDto.phoneNumber(),
            requestDto.slackId());

        if (!checkUserList.isEmpty()) {
            throw new UserException(ExceptionMessage.ALREADY_EXIST_DATA);
        }

        userInfo.updateUser(requestDto.role(), requestDto.slackId(), requestDto.phoneNumber());

        return UserResponseDto.from(userInfo);
    }

    /**
     * 유저 삭제
     */
    @Transactional
    @Override
    public Long deleteUserById(Long userId) {

        User userInfo = findUserByIdAndCheck(userId);
        userInfo.deleteEntity();

        return userInfo.getId();
    }

    /**
     * 유저 로그인 확인
     */
    @Override
    public UserResponseDto findUserByUsernameAndPassword(UserLoginRequestDto requestDto) {

        // 유저 갖고 옴
        User user = findUserByUsernameAndCheck(requestDto.username());

        //비밀번호 확인
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new UserException(ExceptionMessage.INVALID_PASSWORD);
        }

        return UserResponseDto.from(user);
    }

    /**
     * 유저 아이디가 존재하는 지 확인
     */
    private User findUserByIdAndCheck(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));
    }

    /**
     * 유저 이름이 존재하는 지 확인
     */
    private User findUserByUsernameAndCheck(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserException(ExceptionMessage.USER_NOT_FOUND));
    }
}
