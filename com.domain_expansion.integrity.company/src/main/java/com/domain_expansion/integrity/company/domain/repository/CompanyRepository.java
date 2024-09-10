package com.domain_expansion.integrity.company.domain.repository;

import com.domain_expansion.integrity.company.domain.model.Company;
import java.util.Optional;

public interface CompanyRepository {

    Optional<Company> findByCompanyIdAndIsDeleteFalse(String id);

    Company save(Company company);
}
