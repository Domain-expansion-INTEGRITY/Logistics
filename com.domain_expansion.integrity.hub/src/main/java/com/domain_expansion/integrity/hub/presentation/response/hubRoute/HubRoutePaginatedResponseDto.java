package com.domain_expansion.integrity.hub.presentation.response.hubRoute;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder(access = AccessLevel.PRIVATE)
public record HubRoutePaginatedResponseDto(
        List<HubRouteResponseDto> content,
        int currentPage,
        int totalPages,
        long totalElements
) {

    public static HubRoutePaginatedResponseDto of(Page<HubRouteResponseDto> page) {
        return HubRoutePaginatedResponseDto.builder()
                .content(page.getContent())
                .currentPage(page.getNumber())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
