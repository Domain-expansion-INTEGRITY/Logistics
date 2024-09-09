package com.domain_expansion.integrity.company.domain.mapper;

import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.model.vo.CompanyAddress;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
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
                new CompanyAddress(requestDto.address())
        );
    }

}
