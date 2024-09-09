package com.domain_expansion.integrity.delivery.presentation.controller;

import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_CREATE_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_GET_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_GET_DELIVERIES;
import static com.domain_expansion.integrity.delivery.common.message.SuccessMessage.SUCCESS_UPDATE_DELIVERY;
import static com.domain_expansion.integrity.delivery.common.response.SuccessResponse.success;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.domain_expansion.integrity.delivery.application.service.DeliveryService;
import com.domain_expansion.integrity.delivery.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.delivery.common.response.CommonResponse;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryCreateRequestDto;
import com.domain_expansion.integrity.delivery.presentation.request.DeliveryUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<? extends CommonResponse> getDelivery(
            @PathVariable
            String deliveryId
    ) {

        return ResponseEntity.status(SUCCESS_GET_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_GET_DELIVERY.getMessage(), deliveryService.getDelivery(deliveryId)));
    }

    @GetMapping
    @DefaultPageSize
    public ResponseEntity<? extends CommonResponse> getDeliveries(
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC)
            Pageable pageable
    ) {

        return ResponseEntity.status(SUCCESS_GET_DELIVERIES.getHttpStatus())
                .body(success(SUCCESS_GET_DELIVERIES.getMessage(), deliveryService.getDeliveries(pageable)));
    }

    @PatchMapping("/{deliveryId}")
    public ResponseEntity<? extends CommonResponse> updateDelivery(
            @Valid @RequestBody
            DeliveryUpdateRequestDto requestDto,
            @PathVariable
            String deliveryId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_UPDATE_DELIVERY.getMessage(), deliveryService.updateDelivery(requestDto, deliveryId)));
    }

    @DeleteMapping("/{deliveryId}")
    public ResponseEntity<? extends CommonResponse> deleteDelivery(
            @PathVariable
            String deliveryId
    ) {

        deliveryService.deleteDelivery(deliveryId);

        return ResponseEntity.status(SUCCESS_CREATE_DELIVERY.getHttpStatus())
                .body(success(SUCCESS_CREATE_DELIVERY.getMessage()));
    }
}
