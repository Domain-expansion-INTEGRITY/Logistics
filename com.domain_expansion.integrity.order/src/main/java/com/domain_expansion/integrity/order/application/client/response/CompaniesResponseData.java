package com.domain_expansion.integrity.order.application.client.response;

import java.util.List;
import lombok.Getter;

@Getter
public class CompaniesResponseData extends CommonResponseData {

    private final List<CompaniesResponseDto> data;

    public CompaniesResponseData(boolean success, String message, List<CompaniesResponseDto> data) {
        super(success, message);
        this.data = data;
    }

    public record CompaniesResponseDto(
            String companyId,
            String userId,
            String hubId,
            String name,
            String type,
            String address
    ) {
    }
}
