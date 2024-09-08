package com.domain_expansion.integrity.user.domain.repository;

import com.domain_expansion.integrity.user.infrastructure.repository.JpaUserRepository;

public interface UserRepository extends JpaUserRepository, UserCustomRepository {

}
