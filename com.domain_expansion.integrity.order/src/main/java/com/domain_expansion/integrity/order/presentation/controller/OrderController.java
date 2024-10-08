package com.domain_expansion.integrity.order.presentation.controller;

import static com.domain_expansion.integrity.order.common.message.SuccessMessage.SUCCESS_CREATE_ORDER;
import static com.domain_expansion.integrity.order.common.message.SuccessMessage.SUCCESS_GET_ORDER;
import static com.domain_expansion.integrity.order.common.message.SuccessMessage.SUCCESS_GET_ORDERS;
import static com.domain_expansion.integrity.order.common.message.SuccessMessage.SUCCESS_UPDATE_ORDER;
import static com.domain_expansion.integrity.order.common.response.SuccessResponse.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

import com.domain_expansion.integrity.order.application.service.OrderService;
import com.domain_expansion.integrity.order.common.aop.DefaultPageSize;
import com.domain_expansion.integrity.order.common.response.CommonResponse;
import com.domain_expansion.integrity.order.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.order.presentation.request.OrderCreateRequestDto;
import com.domain_expansion.integrity.order.presentation.request.OrderSearchCondition;
import com.domain_expansion.integrity.order.presentation.request.OrderUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> createOrder(
            @Valid @RequestBody
            OrderCreateRequestDto requestDto
    ) {

        return ResponseEntity.status(SUCCESS_CREATE_ORDER.getHttpStatus())
                .body(success(SUCCESS_CREATE_ORDER.getMessage(), orderService.createOrder(requestDto)));
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> getOrder(
            @PathVariable
            String orderId,
            @AuthenticationPrincipal
            UserDetailsImpl userDetails
    ) {

        return ResponseEntity.status(SUCCESS_GET_ORDER.getHttpStatus())
                .body(success(SUCCESS_GET_ORDER.getMessage(), orderService.getOrder(orderId, userDetails)));
    }

    @GetMapping
    @DefaultPageSize
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER', 'ROLE_HUB_DELIVERY_MAN', 'ROLE_HUB_COMPANY', 'ROLE_HUB_COMPANY_DELIVERY_MAN')")
    public ResponseEntity<? extends CommonResponse> getOrders(
            @PageableDefault(size = 10, sort = "createdAt", direction = DESC)
            Pageable pageable,
            @AuthenticationPrincipal
            UserDetailsImpl userDetails,
            @ModelAttribute
            OrderSearchCondition
            condition
    ) {

        return ResponseEntity.status(SUCCESS_GET_ORDERS.getHttpStatus())
                .body(success(SUCCESS_GET_ORDERS.getMessage(), orderService.getOrdersByCondition(pageable, userDetails, condition)));
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_MASTER', 'ROLE_HUB_MANAGER')")
    public ResponseEntity<? extends CommonResponse> updateOrder(
            @Valid @RequestBody
            OrderUpdateRequestDto requestDto,
            @PathVariable
            String orderId
    ) {

        return ResponseEntity.status(SUCCESS_UPDATE_ORDER.getHttpStatus())
                .body(success(SUCCESS_UPDATE_ORDER.getMessage(), orderService.updateOrder(requestDto, orderId)));
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_MASTER')")
    public ResponseEntity<? extends CommonResponse> deleteOrder(
            @PathVariable
            String orderId
    ) {

        orderService.deleteOrder(orderId);

        return ResponseEntity.status(SUCCESS_CREATE_ORDER.getHttpStatus())
                .body(success(SUCCESS_CREATE_ORDER.getMessage()));
    }
}
