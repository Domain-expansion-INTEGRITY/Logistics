package com.domain_expansion.integrity.user.domain.service;

import com.domain_expansion.integrity.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserDomainServiceImpl implements UserDomainService {

    private final UserRepository userRepository;


}
