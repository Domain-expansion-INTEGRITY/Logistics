package com.domain_expansion.integrity.company.application.service;

import com.domain_expansion.integrity.company.application.client.HubClient;
import com.domain_expansion.integrity.company.application.client.response.HubResponseDto;
import com.domain_expansion.integrity.company.application.mapper.CompanyMapper;
import com.domain_expansion.integrity.company.application.shared.RoleConstants;
import com.domain_expansion.integrity.company.common.exception.CompanyException;
import com.domain_expansion.integrity.company.common.message.ExceptionMessage;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import com.domain_expansion.integrity.company.domain.repository.CompanyQueryRepository;
import com.domain_expansion.integrity.company.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanySearchCondition;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyValidateResponseDto;
import com.github.ksuid.Ksuid;
import java.util.List;
import java.util.stream.Collectors;
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

        if(isHubExists(createRequestDto.hubId()) == false){
            throw new CompanyException(ExceptionMessage.NOT_FOUND_HUB_ID);
        }

        String companyId = Ksuid.newKsuid().toString();

        Company company = companyMapper.CompanyCreateDtoToCompany(createRequestDto,companyId);

        Company newCompany = companyRepository.save(company);

        return CompanyResponseDto.from(newCompany);

    }

    @Transactional
    @Override
    public void deleteCompany(String companyId, UserDetailsImpl userDetails) {

        String role =  userDetails.getRole();

        Long userId = userDetails.getUserId();

        Company existedCompany = isExistsCompany(companyId);

        if(RoleConstants.ROLE_HUB_MANAGER.equals(role))
        {
            if(isHaveAuthorized(existedCompany.getCompanyId(),userId))
            {
                existedCompany.deleteCompany(userId);
            }else {
                throw new CompanyException(ExceptionMessage.NOT_AUTHORIZED);
            }
        }
        else
        {
            existedCompany.deleteCompany(userId);
        }
    }

    @Override
    public CompanyResponseDto getCompany(String companyId) {

        Company existedCompany = isExistsCompany(companyId);

        return CompanyResponseDto.from(existedCompany);

    }

    /***
     * 업체명과 업체타입으러 검색가능
     * @param pageable
     * @return
     */
    @Override
    public Page<CompanyResponseDto> getCompanies(CompanySearchCondition searchCondition,Pageable pageable) {
        return companyQueryRepository.searchCompanies(searchCondition,pageable);
    }

    @Override
    public CompanyValidateResponseDto validateUser(String companyId, Long userId) {

        boolean result = companyRepository.findByCompanyIdAndUserIdAndIsDeleteFalse(companyId,userId).isPresent();
        return CompanyValidateResponseDto.of(result);
    }

    @Transactional
    @Override
    public CompanyResponseDto updateCompany(CompanyUpdateRequestDto requestDto, String companyId,
            UserDetailsImpl userDetails) {

        if(isHubExists(requestDto.hubId()) == false){
            throw new CompanyException(ExceptionMessage.NOT_FOUND_HUB_ID);
        }

        Company existedCompany = isExistsCompany(companyId);

        String role =  userDetails.getRole();

        Long userId = userDetails.getUserId();

        if(RoleConstants.ROLE_HUB_COMPANY.equals(role)){

            if(userId.equals(existedCompany.getUserId()))
            {
                existedCompany.updateWith(requestDto);
            }else {
                throw new CompanyException(ExceptionMessage.NOT_AUTHORIZED);
            }

        }else if(RoleConstants.ROLE_HUB_MANAGER.equals(role))
        {
            if(isHaveAuthorized(existedCompany.getCompanyId(),userId))
            {
                //해당 업체에 대해 수정
                existedCompany.updateWith(requestDto);
            }else {
                throw new CompanyException(ExceptionMessage.NOT_AUTHORIZED);
            }

        }else{
            existedCompany.updateWith(requestDto);
        }

        return CompanyResponseDto.from(existedCompany);

    }

    @Override
    public CompanyResponseDto getCompanyByUserId(Long userId) {

        return CompanyResponseDto.from(companyRepository.findByUserId(userId).orElseThrow(
                () -> new CompanyException(ExceptionMessage.NOT_FOUND_COMPANY_ID)
        ));
    }

    @Override
    public List<CompanyResponseDto> getCompanyByHubId(String hubId) {

        return companyRepository.findAllByHubId(hubId)
                .stream().map(CompanyResponseDto::from).collect(Collectors.toList());
    }

    /***
     *  허브관리자인 경우자신의 허브에 소속된 업체인지 검증
     */
    private boolean isHaveAuthorized(String companyId, Long userId)
    {

        HubResponseDto dto = hubClient.findHubByUserId(userId);

        return companyRepository.findByCompanyIdAndHubIdAndIsDeleteFalse(companyId,dto.hubId()).isPresent();
    }

    /***
     * 허브 ID가 존재하는지 검증
     * @param hubId
     * @return
     */
    private boolean isHubExists(String hubId) {

        hubClient.findHubById(hubId);
        return true;

    }

    /***
     * 해당 업체가 존재하는지 검증
     * @param companyId
     * @return
     */
    private Company isExistsCompany(String companyId) {

        return companyRepository.findByCompanyIdAndIsDeleteFalse(companyId).orElseThrow(
                () -> new CompanyException(ExceptionMessage.NOT_FOUND_COMPANY_ID)
        );

    }
}
