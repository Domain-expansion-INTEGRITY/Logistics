package com.domain_expansion.integrity.company.application.mapper;

import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company CompanyCreateDtoToCompany(CompanyCreateRequestDto requestDto, String companyId){
        return Company.from(
                companyId,
                requestDto.userId(),
                requestDto.hubId(),
                requestDto.name(),
                requestDto.companyType(),
                requestDto.address()
        );
    }

}
