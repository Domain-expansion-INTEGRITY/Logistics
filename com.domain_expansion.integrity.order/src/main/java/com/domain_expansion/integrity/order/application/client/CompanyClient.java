package com.domain_expansion.integrity.order.application.client;

import com.domain_expansion.integrity.order.application.client.response.CompaniesResponseData;
import com.domain_expansion.integrity.order.application.client.response.CompanyResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "company-service")
public interface CompanyClient {

    @GetMapping("/api/v1/companies/user/{userId}")
    ResponseEntity<CompanyResponseData> getCompanyByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/v1/companies/hub/{hubId}")
    ResponseEntity<CompaniesResponseData> getCompaniesByHubId(@PathVariable("hubId") String hubId);
}
