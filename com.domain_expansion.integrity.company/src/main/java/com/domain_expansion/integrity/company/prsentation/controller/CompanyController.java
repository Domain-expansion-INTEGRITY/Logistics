package com.domain_expansion.integrity.company.prsentation.controller;

import static com.domain_expansion.integrity.company.common.message.SuccessMessage.*;
import com.domain_expansion.integrity.company.application.service.CompanyService;
import com.domain_expansion.integrity.company.common.response.CommonResponse;
import com.domain_expansion.integrity.company.common.response.SuccessResponse;
import com.domain_expansion.integrity.company.domain.model.CompanyType;
import com.domain_expansion.integrity.company.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
    private final CompanyService companyService;

    /***
     * 업체 등록
     * @param createRequestDto
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER, "
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_COMPANY)")
    @PostMapping
    public ResponseEntity<? extends CommonResponse> createCompany(@RequestBody CompanyCreateRequestDto createRequestDto) {
        return ResponseEntity.status(SUCCESS_CREATE_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_CREATE_COMPANY.getMessage(), companyService.createCompany(createRequestDto)));
    }

    /***
     * 업체 단건 조회
     * @param companyId
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER, "
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_COMPANY,"
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @GetMapping("/{company_id}")
    public ResponseEntity<?  extends CommonResponse> findCompanyById(@PathVariable("company_id") String companyId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info(userDetails.getAuthorities().toString());
        return ResponseEntity.status(SUCCESS_GET_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_GET_COMPANY.getMessage(), companyService.getCompany(companyId)));
    }

    /***
     * 업체 목록조회
     * @param
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER, "
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_COMPANY,"
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @GetMapping
    public ResponseEntity<?  extends CommonResponse> findAllCompany(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) CompanyType companyType,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.status(SUCCESS_GET_COMPANY.getHttpStatus()).body(
                SuccessResponse.of(SUCCESS_GET_COMPANY.getMessage(), companyService.getCompanies(companyName,companyType,pageable)));
    }

    /***
     * 업체 수정
     * @param companyId
     * @param
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER, "
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_COMPANY,"
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @PatchMapping("{company_id}")
    public ResponseEntity<?  extends CommonResponse> updateCompany(@PathVariable("company_id") String companyId
            , @RequestBody CompanyUpdateRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(SUCCESS_UPDATE_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_UPDATE_COMPANY.getMessage(), companyService.updateCompany(requestDto,companyId,userDetails)));
    }

    /***
     * 업체 삭제(soft delete)
     * @param companyId
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER, "
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @DeleteMapping("{company_id}")
    public ResponseEntity<?  extends CommonResponse> deleteCompany(@PathVariable("company_id") String companyId
    ,@AuthenticationPrincipal UserDetailsImpl userDetails) {

        companyService.deleteCompany(companyId, userDetails);

        return ResponseEntity.status(SUCCESS_DELETE_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_DELETE_COMPANY.getMessage()));

    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER, "
            + "T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_HUB_COMPANY)")
    @GetMapping("/{company_id}/validate/user")
    public ResponseEntity<?  extends CommonResponse> validateUserInCompany(@PathVariable("company_id") String companyId
            ,@RequestParam Long userId) {

        return ResponseEntity.status(SUCCESS_GET_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_GET_COMPANY.getMessage(),companyService.validateUser(companyId,userId)));

    }
}
