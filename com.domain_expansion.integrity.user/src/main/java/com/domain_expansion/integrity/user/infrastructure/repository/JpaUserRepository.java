package com.domain_expansion.integrity.user.infrastructure.repository;

import com.domain_expansion.integrity.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

}
