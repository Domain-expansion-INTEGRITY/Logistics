package com.domain_expansion.integrity.hub.presentation.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder(access = AccessLevel.PRIVATE)
public record HubPaginatedResponseDto(
        List<HubResponseDto> content,
        int currentPage,
        int totalPages,
        long totalElements
) {

    public static HubPaginatedResponseDto of(Page<HubResponseDto> page) {
        return HubPaginatedResponseDto.builder()
                .content(page.getContent())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
