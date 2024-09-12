package com.domain_expansion.integrity.company;

import static org.junit.jupiter.api.Assertions.*;
import com.domain_expansion.integrity.company.application.client.HubClient;
import com.domain_expansion.integrity.company.application.client.response.HubResponseDto;
import com.domain_expansion.integrity.company.application.mapper.CompanyMapper;
import com.domain_expansion.integrity.company.application.service.CompanyServiceImpl;
import com.domain_expansion.integrity.company.application.shared.RoleConstants;
import com.domain_expansion.integrity.company.common.exception.CompanyException;
import com.domain_expansion.integrity.company.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.domain.model.CompanyType;
import com.domain_expansion.integrity.company.domain.repository.CompanyQueryRepository;
import com.domain_expansion.integrity.company.domain.repository.CompanyRepository;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanySearchCondition;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyResponseDto;
import com.domain_expansion.integrity.company.prsentation.response.CompanyValidateResponseDto;
import com.github.ksuid.Ksuid;
import feign.FeignException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private CompanyQueryRepository companyQueryRepository;
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private HubClient hubClient;
    @InjectMocks
    private CompanyServiceImpl companyService;
    @Mock
    private UserDetailsImpl userDetails;

    private String companyId;

    @BeforeEach
    void setUp() {
        companyId = Ksuid.newKsuid().toString();

    }

    @Nested
    @DisplayName("업체 등록 테스트 모음")
    class createCompany{

        @Test
        @DisplayName("업체 등록 - 존재하지 않는 허브 ID인 경우")
        void testErrorCreateCompanyNotExistHubId() {

            String expectedMessage = "관리 허브ID가 존재하지 않습니다.";
            String hubId = Ksuid.newKsuid().toString();

            CompanyCreateRequestDto dto = new CompanyCreateRequestDto(
                    1L,                     // userId
                    hubId,              // hubId
                    "Test Company",         // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "123 Main Street"       // address
            );


            // Mock 설정
            FeignException.NotFound feignNotFoundException = mock(FeignException.NotFound.class);
            given(hubClient.findHubById(hubId)).willThrow(feignNotFoundException);
            // 예외 발생 테스트
            CompanyException thrown = assertThrows(CompanyException.class,
                    () -> companyService.createCompany(dto)
            );

            assertEquals(expectedMessage, thrown.getMessage());

        }

        @Test
        @DisplayName("업체 등록 - feignCLient에서 호출을 제대로 하지 못한 경우")
        void testErrorCreateCompanyFailedFeignClient() {

            String expectedMessage = "허브 정보를 확인하는 도중 문제가 발생했습니다.";
            String hubId = Ksuid.newKsuid().toString();

            CompanyCreateRequestDto dto = new CompanyCreateRequestDto(
                    1L,                     // userId
                    hubId,              // hubId
                    "Test Company",         // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "123 Main Street"       // address
            );


            // Mock 설정
            FeignException feignException = mock(FeignException.class);
            given(hubClient.findHubById(hubId)).willThrow(feignException);
            // 예외 발생 테스트
            Exception thrown = assertThrows(RuntimeException.class,
                    () -> companyService.createCompany(dto)
            );

            assertEquals(expectedMessage, thrown.getMessage());

        }

        @Test
        @DisplayName("업체 등록 - 성공적으로 등록")
        void testSuccessCreateCompany(){

            String hubId = Ksuid.newKsuid().toString(); // 허브 ID

            CompanyCreateRequestDto dto = new CompanyCreateRequestDto(
                    1L,                     // userId
                    hubId,              // hubId
                    "Test Company",         // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "123 Main Street"       // address
            );

            Ksuid mockCompanyId = mock(Ksuid.class);

            given(mockCompanyId.toString()).willReturn(companyId);
            Mockito.mockStatic(Ksuid.class).when(Ksuid::newKsuid).thenReturn(mockCompanyId);

            HubResponseDto hubResponse = new HubResponseDto(hubId, 1L,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubById(hubId)).willReturn(hubResponse);

            Company company = new Company(companyId, 1L, hubId,"Test Company", CompanyType.PRODUCING_COMPANY, "123 Main Street");
            given(companyMapper.CompanyCreateDtoToCompany(dto, companyId)).willReturn(company);

            Company savedCompany = new Company(companyId, 1L,hubId, "Test Company", CompanyType.PRODUCING_COMPANY, "123 Main Street");
            given(companyRepository.save(company)).willReturn(savedCompany);

            CompanyResponseDto result = companyService.createCompany(dto);

            assertNotNull(result);
            assertEquals(result.companyId(), companyId);
            assertEquals(result.name(), dto.name());
        }
    }

    @Nested
    @DisplayName("업체 삭제 테스트 모음")
    class deleteCompany{
        @Test
        @DisplayName("업체 삭제 - 존재하지 않는 업체 ID")
        void testErrorDeleteCompanyNotExistCompanyId(){

            String expected = "존재하지 않는 업체입니다.";

            given(userDetails.getUserId()).willReturn(1L);
            given(userDetails.getRole()).willReturn("ROLE_MASTER");

            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(Optional.empty());

            Exception exception = assertThrows(CompanyException.class,
                    () -> companyService.deleteCompany(companyId,userDetails));

            assertEquals(expected, exception.getMessage());
        }
        @Test
        @DisplayName("업체 삭제 - 허브 매니저이지만 허브를 가지지 않은 경우")
        void testErrorDeleteCompanyNotHub(){

            String expected = "접근 권한이 없습니다.";
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_MANAGER;

            Company company = Company.from(companyId,userId,hubId,"test",CompanyType.PRODUCING_COMPANY,"test");

            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.ofNullable(company));

            HubResponseDto dto = new HubResponseDto(
                    hubId,
                    1L,
                    "test",
                    "test",
                    123,
                    90
            );

            FeignException.NotFound feignNotFoundException = mock(FeignException.NotFound.class);
            given(hubClient.findHubByUserId(1L)).willThrow(feignNotFoundException);

            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);

            Exception exception = assertThrows(CompanyException.class,
                    () -> companyService.deleteCompany(companyId,userDetails));

            assertEquals(expected, exception.getMessage());
        }

        @Test
        @DisplayName("업체 삭제 - 허브 매니저이지만 자신의 업체가 아닌 경우")
        void testErrorDeleteCompanyNotHubManager(){

            String expected = "접근 권한이 없습니다.";
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_MANAGER;
            Company company = Company.from(companyId,userId,hubId,"test",CompanyType.PRODUCING_COMPANY,"test");


            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.ofNullable(company));

            HubResponseDto dto = new HubResponseDto(
                    hubId,
                    1L,
                    "test",
                    "test",
                    123,
                    90
            );

            given(hubClient.findHubByUserId(1L)).willReturn(dto);

            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);


            given(companyRepository.findByCompanyIdAndHubIdAndIsDeleteFalse(companyId, dto.hubId())).willReturn(Optional.empty());

            Exception exception = assertThrows(CompanyException.class,
                    () -> companyService.deleteCompany(companyId,userDetails));

            assertEquals(expected, exception.getMessage());
        }

        @Test
        @DisplayName("업체 삭제 - 더티 체킹 테스트")
        void testSuccessDeleteCompanyHubManager(){

            String expected = "접근 권한이 없습니다.";
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_MANAGER;

            Company company = Company.from(companyId,userId,hubId,"test",CompanyType.PRODUCING_COMPANY,"test");

            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.ofNullable(company));

            company.deleteCompany(userId);

            assertEquals(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId).get().getIsDelete(),true);
            assertEquals(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId).get().getDeletedBy(),userId);
        }
    }

    @Nested
    @DisplayName("업체 단건 조회 테스트 모음")
    class getCompany{
        @Test
        @DisplayName("업체 단건 조회 - 존재하지 않는 업체ID")
        void testErrorGetCompanyNotCompanyId(){

            String expected = "존재하지 않는 업체입니다.";

            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(Optional.empty());

            Exception exception = assertThrows(CompanyException.class,
                    () -> companyService.getCompany(companyId));

            assertEquals(expected, exception.getMessage());

        }

        @Test
        @DisplayName("업체 단건 조회 - 성공")
        void testSuccessGetCompany(){

            String hubId = Ksuid.newKsuid().toString();
            Company company = Company.from(companyId,2L,hubId,"test",CompanyType.PRODUCING_COMPANY,"test");

            given(companyRepository.findByCompanyIdAndIsDeleteFalse(company.getCompanyId())).willReturn(
                    Optional.of(company));

            CompanyResponseDto result = companyService.getCompany(companyId);

            assertNotNull(result);
            assertEquals(result.companyId(), company.getCompanyId());
            assertEquals(result.name(), "test");

        }
    }

    @Nested
    @DisplayName("업체 검색 조회 테스트 모음")
    class getAllCompany{

        @Test
        @DisplayName("업체 검색 조회 - 조건에 맞는 목록 리스트")
        void testSuccessGetAllCompany(){

            Pageable pageable = PageRequest.of(0,10);
            String hubId = Ksuid.newKsuid().toString();

            Company company = new Company(companyId,1L,hubId,"test",CompanyType.PRODUCING_COMPANY,"test");

            Page<Company> companyPage = new PageImpl<>(List.of(company));
            Page<CompanyResponseDto> lists = companyPage.map(CompanyResponseDto::from);

            CompanySearchCondition searchCondition = new CompanySearchCondition(
                    "test",
                    CompanyType.PRODUCING_COMPANY

            );
            given(companyQueryRepository.searchCompanies(searchCondition,pageable)).willReturn(lists);

            Page<CompanyResponseDto> result = companyService.getCompanies(searchCondition,pageable);

            assertNotNull(result);
            assertEquals(result.getContent().size(),1);
            assertEquals(result.getContent().get(0).name(),"test");
        }

    }


    @Nested
    @DisplayName("업체 수정 테스트 모음")
    class updateCompany{
        @Test
        @DisplayName("업체 수정 - 존재하지 않는 허브 ID")
        void testErrorUpdateCompanyNotExistHubId(){

            String hubId = Ksuid.newKsuid().toString();
            String expectedMessage = "관리 허브ID가 존재하지 않습니다.";

            CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto(
                    hubId,                     // hubId
                    "Updated Company Name",   // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "Updated Address"          // address
            );

            FeignException.NotFound feignNotFoundException = mock(FeignException.NotFound.class);
            given(hubClient.findHubById(hubId)).willThrow(feignNotFoundException);

            CompanyException thrown = assertThrows(CompanyException.class,
                    () -> companyService.updateCompany(requestDto, companyId, userDetails)
            );

            assertEquals(expectedMessage, thrown.getMessage());
        }

        @Test
        @DisplayName("업체 수정 - 허브업체인데 자신의 업체가 아닌 경우")
        void testErrorUpdateCompanyByHubCompany()
        {
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_COMPANY;

            String expectedMessage = "접근 권한이 없습니다.";

            CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto(
                    hubId,                     // hubId
                    "Updated Company Name",   // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "Updated Address"          // address
            );

            HubResponseDto hubResponse = new HubResponseDto(hubId, userId,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubById(hubId)).willReturn(hubResponse); // 허브 존재 검증

            Company existedCompany = new Company(companyId, 2L, hubId,"Old Name", CompanyType.PRODUCING_COMPANY, "Old Address");
            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.of(existedCompany)); // 회사 존재 검증


            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);

            Exception exception = assertThrows(CompanyException.class,
                    () -> companyService.updateCompany(requestDto, companyId, userDetails)
            );
            assertEquals(expectedMessage, exception.getMessage());

        }

        @Test
        @DisplayName("업체 수정 - 허브업체인데 자신의 업체인 경우")
        void testSuccessUpdateCompanyByHubCompany()
        {
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_COMPANY;

            CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto(
                    hubId,                     // hubId
                    "Updated Company Name",   // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "Updated Address"          // address
            );

            HubResponseDto hubResponse = new HubResponseDto(hubId, userId,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubById(hubId)).willReturn(hubResponse); // 허브 존재 검증

            Company existedCompany = new Company(companyId, userId, hubId,"Old Name", CompanyType.PRODUCING_COMPANY, "Old Address");
            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.of(existedCompany)); // 회사 존재 검증

            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);


            CompanyResponseDto responseDto = companyService.updateCompany(requestDto, companyId, userDetails);

            assertNotNull(responseDto);
            assertEquals(requestDto.name(),responseDto.name());
            assertEquals(requestDto.address(),responseDto.address());

        }

        @Test
        @DisplayName("업체 수정 - 허브 매니저이지만 자신의 허브의 소속된 업체이 아닌 경우")
        void testErrorUpdateCompanyByHubManager(){
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_MANAGER;

            String expectedMessage = "접근 권한이 없습니다.";

            CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto(
                    hubId,                     // hubId
                    "Updated Company Name",   // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "Updated Address"          // address
            );

            HubResponseDto hubResponse = new HubResponseDto(hubId, userId,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubById(hubId)).willReturn(hubResponse); // 허브 존재 검증

            Company existedCompany = new Company(companyId, 2L, hubId,"Old Name", CompanyType.PRODUCING_COMPANY, "Old Address");
            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.of(existedCompany)); // 회사 존재 검증

            FeignException.NotFound feignNotFoundException = mock(FeignException.NotFound.class);
            given(hubClient.findHubByUserId(userId)).willThrow(feignNotFoundException);

            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);

            Exception exception = assertThrows(CompanyException.class,
                    () -> companyService.updateCompany(requestDto, companyId, userDetails)
            );

            assertEquals(expectedMessage, exception.getMessage());
        }

        @Test
        @DisplayName("업체 수정 - 허브 매니저이지만 자신의 허브에 소속된 업체를 수정하는 경우")
        void testSuccessUpdateCompanyByHubManager(){
            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_HUB_MANAGER;

            CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto(
                    hubId,                     // hubId
                    "Updated Company Name",   // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "Updated Address"          // address
            );

            HubResponseDto hubResponse = new HubResponseDto(hubId, userId,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubById(hubId)).willReturn(hubResponse); // 허브 존재 검증

            Company existedCompany = new Company(companyId, userId, hubId,"Old Name", CompanyType.PRODUCING_COMPANY, "Old Address");
            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.of(existedCompany)); // 회사 존재 검증

            HubResponseDto dto = new HubResponseDto(hubId, userId,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubByUserId(userId)).willReturn(dto);

            Company company = mock(Company.class);
            given(companyRepository.findByCompanyIdAndHubIdAndIsDeleteFalse(companyId,dto.hubId())).willReturn(Optional.of(company));

            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);

            CompanyResponseDto result = companyService.updateCompany(requestDto, companyId, userDetails);

            assertNotNull(result);
            assertEquals(requestDto.name(),result.name());
            assertEquals(requestDto.address(),result.address());
        }

        @Test
        @DisplayName("업체 업데이트 - 관리자가 수정하는 경우")
        void testSuccessUpdateCompanyWithValidHubAndRole() {

            String hubId = Ksuid.newKsuid().toString();
            Long userId = 1L;
            String role = RoleConstants.ROLE_MASTER;

            CompanyUpdateRequestDto requestDto = new CompanyUpdateRequestDto(
                    hubId,                     // hubId
                    "Updated Company Name",   // name
                    CompanyType.PRODUCING_COMPANY,    // companyType
                    "Updated Address"          // address
            );

            HubResponseDto hubResponse = new HubResponseDto(hubId, userId,"Test Hub", "Test Address",10,10);
            given(hubClient.findHubById(hubId)).willReturn(hubResponse); // 허브 존재 검증

            Company existedCompany = new Company(companyId, userId, hubId,"Old Name", CompanyType.PRODUCING_COMPANY, "Old Address");
            given(companyRepository.findByCompanyIdAndIsDeleteFalse(companyId)).willReturn(
                    Optional.of(existedCompany)); // 회사 존재 검증

            UserDetailsImpl userDetails = new UserDetailsImpl(userId,null,role);

            CompanyResponseDto result = companyService.updateCompany(requestDto, companyId, userDetails);

            assertNotNull(result);
            assertEquals(requestDto.name(),result.name());
            assertEquals(requestDto.address(),result.address());
        }
    }

    @Nested
    @DisplayName("유저가 업체를 가지고 있는지 검증 테스트 모음")
    class validateCompany{

        @Test
        @DisplayName("해당 유저가 업체의 사장님인 경우 ")
        void testSuccessValidateUser(){

            Long userId = 1L;

            Company company = mock(Company.class);

            given(companyRepository.findByCompanyIdAndUserIdAndIsDeleteFalse(companyId,userId)).willReturn(Optional.of(company));

            CompanyValidateResponseDto result = companyService.validateUser(companyId,userId);

            assertEquals(result.isOwner(),true);
        }

        @Test
        @DisplayName("해당 유저가 업체의 사장님가 아닌 경우 ")
        void testFailValidateUser(){

            Long userId = 1L;

            given(companyRepository.findByCompanyIdAndUserIdAndIsDeleteFalse(companyId,userId)).willReturn(Optional.empty());

            CompanyValidateResponseDto result = companyService.validateUser(companyId,userId);

            assertEquals(result.isOwner(),false);
        }
    }

}