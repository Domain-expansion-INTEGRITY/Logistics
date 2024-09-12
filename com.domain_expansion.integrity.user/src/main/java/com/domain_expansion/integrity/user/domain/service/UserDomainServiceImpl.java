package com.domain_expansion.integrity.user.domain.service;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.domain.repository.UserQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserQueryRepository userQueryRepository;

    /**
     * 중복이 있는지 확인하는 함수들
     */
    @Override
    public List<User> checkPhoneNumber(Long userId, String phoneNumber, String slackId) {
        return userQueryRepository.checkExistFieldInfo(userId, phoneNumber, slackId);
    }
}
