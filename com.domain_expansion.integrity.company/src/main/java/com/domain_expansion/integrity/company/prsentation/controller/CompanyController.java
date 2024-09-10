package com.domain_expansion.integrity.company.prsentation.controller;

import static com.domain_expansion.integrity.company.common.message.SuccessMessage.*;
import com.domain_expansion.integrity.company.application.service.CompanyService;
import com.domain_expansion.integrity.company.common.response.CommonResponse;
import com.domain_expansion.integrity.company.common.response.SuccessResponse;
import com.domain_expansion.integrity.company.domain.model.Company;
import com.domain_expansion.integrity.company.prsentation.request.CompanyCreateRequestDto;
import com.domain_expansion.integrity.company.prsentation.request.CompanyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    /***
     * 업체 등록
     * @param createRequestDto
     * @return
     */
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
    @GetMapping("/{company_id}")
    public ResponseEntity<?> findCompanyById(@PathVariable("company_id") String companyId) {
        return ResponseEntity.status(SUCCESS_GET_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_GET_COMPANY.getMessage(), companyService.getCompany(companyId)));
    }

    /***
     * 업체 목록조회
     * @param
     * @return
     */
    @GetMapping
    public ResponseEntity<?> findAllCompany(

            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /***
     * 업체 수정
     * @param companyId
     * @param
     * @return
     */
    @PatchMapping("{company_id}")
    public ResponseEntity<?> updateCompany(@PathVariable("company_id") String companyId
            , @RequestBody CompanyUpdateRequestDto requestDto) {
        return ResponseEntity.status(SUCCESS_DELETE_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_DELETE_COMPANY.getMessage(), companyService.updateCompany(requestDto,companyId)));
    }

    /***
     * 업체 삭제(soft delete)
     * @param companyId
     * @return
     */
    @DeleteMapping("{company_id}")
    public ResponseEntity<?> deleteCompany(@PathVariable("company_id") String companyId) {

        companyService.deleteCompany(companyId);

        return ResponseEntity.status(SUCCESS_DELETE_COMPANY.getHttpStatus())
                .body(SuccessResponse.of(SUCCESS_DELETE_COMPANY.getMessage()));
    }
}
