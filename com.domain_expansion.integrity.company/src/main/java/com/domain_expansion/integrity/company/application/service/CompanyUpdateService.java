package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;

public interface CompanyUpdateService {
    CompanyResponseDto updateCompany(CompanyUpdateRequestDto requestDto,String companyId,
            UserDetailsImpl userDetails );
}
