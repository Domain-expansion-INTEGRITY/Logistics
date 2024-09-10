package com.domain_expansion.integrity.company.infrastructure.repository;

import com.domain_expansion.integrity.company.domain.model.CompanyType;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyQueryRepository {
    Page<CompanyResponseDto> searchCompanies(String companyName, CompanyType type,Pageable pageable);
}
