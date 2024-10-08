package com.domain_expansion.integrity.company.domain.repository;

import com.domain_expansion.integrity.company.domain.model.Company;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository {

    Optional<Company> findByCompanyIdAndIsDeleteFalse(String id);

    Optional<Company> findByCompanyIdAndUserIdAndIsDeleteFalse(String id,Long userId);

    Company save(Company company);

    Optional<Company> findByCompanyIdAndHubIdAndIsDeleteFalse(String companyId,String hubId);

    Optional<Company> findByHubIdAndIsDeleteFalse(String hubId);

    Optional<Company> findByUserId(Long userId);

    List<Company> findAllByHubId(String hubId);
}
