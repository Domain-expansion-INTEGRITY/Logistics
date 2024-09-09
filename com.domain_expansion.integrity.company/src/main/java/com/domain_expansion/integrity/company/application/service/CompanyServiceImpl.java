package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.application.mapper.CompanyMapper;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import com.github.ksuid.Ksuid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        String companyId = Ksuid.newKsuid().toString();

        Company company = companyMapper.CompanyCreateDtoToCompany(createRequestDto,companyId);

        Company newCompany = companyRepository.save(company);

        return CompanyResponseDto.from(newCompany);
    }

    @Override
    public void deleteCompany(String companyId) {

    }

    @Override
    public CompanyResponseDto getCompany(String companyId) {
        return null;
    }

    @Override
    public Page<CompanyResponseDto> getCompanies(Pageable pageable) {
        return null;
    }

    @Override
    public CompanyResponseDto updateCompany(CompanyUpdateRequestDto requestDto, String companyId) {
        return null;
    }
}
