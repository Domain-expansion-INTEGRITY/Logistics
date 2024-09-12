package com.domain_expansion.integrity.product.application.client;

import com.domain_expansion.integrity.product.application.client.response.CompanyResponseData;
import com.domain_expansion.integrity.product.application.client.response.ValidateUserResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "company-service")
public interface CompanyClient {

    @GetMapping("/api/v1/companies/{companyId}")
    ResponseEntity<CompanyResponseData> getCompany(@PathVariable String companyId);

    @GetMapping("/api/v1/companies/{companyId}/validate/user")
    ResponseEntity<ValidateUserResponseData> validateUser(
            @RequestParam(value = "userId") Long userId, @PathVariable String companyId);
}
