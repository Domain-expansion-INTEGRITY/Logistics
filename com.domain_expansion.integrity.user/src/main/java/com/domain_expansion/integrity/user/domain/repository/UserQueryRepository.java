package com.domain_expansion.integrity.user.domain.repository;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    Long checkExistFieldInfo(Long userId, String phoneNumber, String slackId);

    Page<User> findAllUserByCondition(Pageable pageable, UserSearchCondition searchCondition);

}
