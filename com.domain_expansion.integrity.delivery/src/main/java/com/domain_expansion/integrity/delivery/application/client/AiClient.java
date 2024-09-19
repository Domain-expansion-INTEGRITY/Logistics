package com.domain_expansion.integrity.delivery.application.client;

import com.domain_expansion.integrity.delivery.application.client.request.CompanyDeliverySequenceRequestDto;
import com.domain_expansion.integrity.delivery.application.client.response.AiResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "ai-service")
public interface AiClient {

    @PostMapping("/api/v1/ais/company-delivery/sequence")
    ResponseEntity<AiResponseData> getCompanyDeliverySequence(@RequestBody CompanyDeliverySequenceRequestDto requestDto);

}
