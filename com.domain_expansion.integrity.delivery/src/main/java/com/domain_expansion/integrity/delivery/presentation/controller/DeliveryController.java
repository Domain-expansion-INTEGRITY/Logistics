package com.domain_expansion.integrity.delivery.presentation.controller;

import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_CREATE_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_DELETE_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_GET_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_GET_DELIVERIES;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_UPDATE_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_UPDATE_DELIVERY_DELIVERY_MAN;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_UPDATE_DELIVERY_HUB_DELIVERY_MAN;
import static com.domain_expansion.integrity.delivery.common.response.SuccessResponse.success;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.domain_expansion.integrity.delivery.application.service.DeliveryService;
import com.domain_expansion.integrity.delivery.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.delivery.common.response.CommonResponse;
import com.domain_expansion.integrity.delivery.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryDeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryHubDeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliverySearchCondition;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/deliveries")
@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<? extends CommonResponse> createDelivery(
            @Valid @RequestBody
            DeliveryCreateRequestDto requestDto
    ) {

        return ResponseEntity.status(SUCCESS_CREATE_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_CREATE_DELIVERY.getMessage(), deliveryService.createDelivery(requestDto)));
    }

    @GetMapping("/{deliveryId}")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> getDelivery(
            @PathVariable
            String deliveryId
    ) {

        return ResponseEntity.status(SUCCESS_GET_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_GET_DELIVERY.getMessage(), deliveryService.getDelivery(deliveryId)));
    }

    @GetMapping
    @DefaultPageSize
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> getDeliveries(
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC)
            Pageable pageable,
            @ModelAttribute
            DeliverySearchCondition searchCondition,
            @AuthenticationPrincipal
            UserDetailsImpl userDetails
    ) {

        return ResponseEntity.status(SUCCESS_GET_DELIVERIES.getHttpStatus())
                .body(success(SUCCESS_GET_DELIVERIES.getMessage(), deliveryService.getDeliveries(pageable, searchCondition, userDetails)));
    }

    @PatchMapping("/{deliveryId}")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> updateDelivery(
            @Valid @RequestBody
            DeliveryUpdateRequestDto requestDto,
            @PathVariable
            String deliveryId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_UPDATE_DELIVERY.getMessage(), deliveryService.updateDelivery(requestDto, deliveryId)));
    }

    @PatchMapping("/{deliveryId}/delivery-man")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> updateDeliveryMan(
            @Valid @RequestBody
            DeliveryDeliveryManUpdateRequestDto requestDto,
            @PathVariable
            String deliveryId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_DELIVERY_DELIVERY_MAN.getHttpStatus())
                .body(success(SUCCESS_UPDATE_DELIVERY_DELIVERY_MAN.getMessage(), deliveryService.updateDeliveryDeliveryMan(requestDto, deliveryId)));
    }

    @PatchMapping("/{deliveryId}/hub-delivery-man")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> updateHubDeliveryMan(
            @Valid @RequestBody
            DeliveryHubDeliveryManUpdateRequestDto requestDto,
            @PathVariable
            String deliveryId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_DELIVERY_HUB_DELIVERY_MAN.getHttpStatus())
                .body(success(SUCCESS_UPDATE_DELIVERY_HUB_DELIVERY_MAN.getMessage(), deliveryService.updateDeliveryHubDeliveryMan(requestDto, deliveryId)));
    }

    @DeleteMapping("/{deliveryId}")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER')")
    public ResponseEntity<? extends CommonResponse> deleteDelivery(
            @PathVariable
            String deliveryId
    ) {

        deliveryService.deleteDelivery(deliveryId);

        return ResponseEntity.status(SUCCESS_DELETE_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_DELETE_DELIVERY.getMessage()));
    }

    //    - **수정**: 마스터 관리자, 해당 허브 관리자, 그리고 해당 배송 담당자만 가능
//- **조회 및 검색**: 모든 로그인 사용자가 가능, 단 배송 담당자는 자신이 담당하는 배송만 조회 가능
}
