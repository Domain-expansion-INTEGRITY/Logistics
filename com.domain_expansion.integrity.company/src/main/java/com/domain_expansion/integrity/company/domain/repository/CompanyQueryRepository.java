package com.domain_expansion.integrity.company.domain.repository;

import com.domain_expansion.integrity.company.prsentation.request.CompanySearchCondition;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyQueryRepository {
    Page<CompanyResponseDto> searchCompanies(CompanySearchCondition searchCondition,Pageable pageable);
}
