package com.domain_expansion.integrity.user.domain.repository;

import com.domain_expansion.integrity.user.domain.model.User;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserQueryRepository {

    List<User> checkExistFieldInfo(Long userId, String phoneNumber, String slackId);

    Page<User> findAllUserByCondition(Pageable pageable, UserSearchCondition searchCondition);

}
