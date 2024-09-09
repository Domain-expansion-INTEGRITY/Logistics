package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;


public interface CompanyService {

    public CompanyResponseDto createCompany(CompanyCreateRequestDto createRequestDto);
}
