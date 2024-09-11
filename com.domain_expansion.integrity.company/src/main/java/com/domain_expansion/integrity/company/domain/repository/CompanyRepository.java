package com.domain_expansion.integrity.company.domain.repository;

import com.domain_expansion.integrity.company.domain.model.Company;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository {

    Optional<Company> findByCompanyIdAndIsDeleteFalse(String id);

    Company save(Company company);

    void deleteById(String companyId);

    Optional<Company> findByCompanyIdAndHubIdAndIsDeleteFalse(String companyId,String hubId);
}
