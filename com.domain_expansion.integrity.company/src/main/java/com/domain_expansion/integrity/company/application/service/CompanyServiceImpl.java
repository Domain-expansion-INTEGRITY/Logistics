package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.application.service.mapper.CompanyMapper;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyResponseDto createCompany(CompanyCreateRequestDto createRequestDto)
    {
        //TODO : hub ID가 존재하는지 검증 필요

        Company company = companyMapper.CompanyCreateDtoToCompany(createRequestDto);

        Company newCompany = companyRepository.save(company);

        return CompanyResponseDto.from(newCompany);
    }
}
