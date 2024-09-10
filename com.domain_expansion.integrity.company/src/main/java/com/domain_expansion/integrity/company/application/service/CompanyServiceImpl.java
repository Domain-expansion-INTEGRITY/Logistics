package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.application.client.HubClient;
import com.domain_expansion.integrity.company.application.client.response.HubResponseDto;
import com.domain_expansion.integrity.company.application.mapper.CompanyMapper;
import com.domain_expansion.integrity.company.common.exception.CompanyException;
import com.domain_expansion.integrity.company.common.message.ExceptionMessage;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.model.CompanyType;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import com.domain_expansion.integrity.company.infrastructure.repository.CompanyQueryRepository;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import com.github.ksuid.Ksuid;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;
    private final CompanyQueryRepository companyQueryRepository;
    private final CompanyMapper companyMapper;
    private final HubClient hubClient;

    @Transactional
    @Override
    public CompanyResponseDto createCompany(CompanyCreateRequestDto createRequestDto)
    {

        if(isHubExists(createRequestDto.hubId())){
            throw new CompanyException(ExceptionMessage.NOT_FOUND_HUB_ID);
        }

        String companyId = Ksuid.newKsuid().toString();

        Company company = companyMapper.CompanyCreateDtoToCompany(createRequestDto,companyId);

        Company newCompany = companyRepository.save(company);

        return CompanyResponseDto.from(newCompany);

    }

    @Transactional
    @Override
    public void deleteCompany(String companyId) {

        Company existedCompany = isExistsCompany(companyId);

        //existedCompany.deleteCompany();
        companyRepository.deleteById(companyId);

    }

    @Override
    public CompanyResponseDto getCompany(String companyId) {

        Company existedCompany = isExistsCompany(companyId);

        return CompanyResponseDto.from(existedCompany);

    }

    @Override
    public Page<CompanyResponseDto> getCompanies(String companyName, CompanyType type,Pageable pageable) {
        return companyQueryRepository.searchCompanies(companyName,type,pageable);
    }

    /***
     * 허브업체 : 자신의 업체만 수정 가능
     * 허브 관리자 : 자신의 허브에 소속된 업체만 관리 가능
     * @param requestDto
     * @param companyId
     * @return
     */
    @Transactional
    @Override
    public CompanyResponseDto updateCompany(CompanyUpdateRequestDto requestDto, String companyId) {

        if(isHubExists(requestDto.hubId())){
            throw new CompanyException(ExceptionMessage.NOT_FOUND_HUB_ID);
        }

        Company existedCompany = isExistsCompany(companyId);

        existedCompany.updateWith(requestDto);

        return CompanyResponseDto.from(existedCompany);

    }

    /***
     * 허브 ID가 존재하는지 검증
     * @param hubId
     * @return
     */
    private boolean isHubExists(String hubId) {

        try{

            HubResponseDto responseDto = hubClient.findHubById(hubId);

            return responseDto == null ? false : true;

        }catch (FeignException.NotFound e) {
            return false;
        }catch (FeignException e) {
            throw new RuntimeException("허브 정보를 확인하는 도중 문제가 발생했습니다.");
        }

    }

    private Company isExistsCompany(String companyId) {

        return companyRepository.findByCompanyIdAndIsDeleteFalse(companyId).orElseThrow(
                () -> new CompanyException(ExceptionMessage.NOT_FOUND_COMPANY_ID)
        );

    }
}
