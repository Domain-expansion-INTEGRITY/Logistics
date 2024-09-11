package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.domain.model.CompanyType;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyValidateResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyReadService {

    CompanyResponseDto getCompany(String companyId);

    Page<CompanyResponseDto> getCompanies(String companyName, CompanyType type, Pageable pageable);

    CompanyValidateResponseDto validateUser(String companyId, Long userId);
}
