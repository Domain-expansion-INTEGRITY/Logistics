package com.domain_expansion.integrity.hub.presentation.controller;

import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_CREATE_DELIVERYMAN;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_CREATE_HUB_DELIVERYMAN;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_DELETE_DELIVERYMAN;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_ALL_HUBS_ROUTE;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_DELIVERYMAN;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_HUB_ROUTE;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_UPDATE_DELIVERYMAN;

import com.domain_expansion.integrity.hub.application.service.deliveryman.HubDeliveryManService;
import com.domain_expansion.integrity.hub.common.response.CommonResponse;
import com.domain_expansion.integrity.hub.common.response.SuccessResponse;
import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.DeliveryManUpdateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.deliveryMan.HubDeliveryManCreateRequestDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/hubs")
@RestController
@RequiredArgsConstructor
public class HubDeliveryManController {

    private final HubDeliveryManService hubDeliveryManService;

    /***
     * 배송당당자 등록
     * @param requestDto
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER)")
    @PostMapping("/deliveryMan")
    public ResponseEntity<?  extends CommonResponse> createDeliveryMan(@RequestBody DeliveryManCreateRequestDto requestDto){

        return ResponseEntity.status(SUCCESS_CREATE_DELIVERYMAN.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_CREATE_DELIVERYMAN.getMessage(),hubDeliveryManService.createDeliveryMan(requestDto)));
    }

    /***
     * 업체 배송당당자 등록
     * @param requestDto
     * @return
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER,"
            + "T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @PostMapping("/{hubId}/deliveryMan")
    public ResponseEntity<?  extends CommonResponse> createHubDeliveryMan(@RequestBody HubDeliveryManCreateRequestDto requestDto,
            @PathVariable String hubId,@AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(SUCCESS_CREATE_HUB_DELIVERYMAN.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_CREATE_HUB_DELIVERYMAN.getMessage(),hubDeliveryManService.createHubDeliveryMan(requestDto,hubId,userDetails)));
    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER,"
            + "T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @GetMapping("/deliveryMan/{deliveryManId}")
    public ResponseEntity<?  extends CommonResponse> getHubDeliveryMan(@PathVariable String deliveryManId,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(SUCCESS_GET_HUB_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_HUB_ROUTE.getMessage(),hubDeliveryManService.getDeliveryManById(deliveryManId,userDetails)));
    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER,"
            + "T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @GetMapping("/deliveryMan")
    public ResponseEntity<?  extends CommonResponse> getAllHubRoutes(@ModelAttribute
    DeliveryManSearchCondition searchCondition, @PageableDefault(size = 10) Pageable pageable,@AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(SUCCESS_GET_ALL_HUBS_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_ALL_HUBS_ROUTE.getMessage(),hubDeliveryManService.getAllDeliveryMans(searchCondition,pageable,userDetails)));

    }

    /***
     * 배송담당자 수정
     */
    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER,"
            + "T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_HUB_MANAGER)")
    @PatchMapping("/deliveryMan/{deliveryManId}")
    public ResponseEntity<?  extends CommonResponse> updateDeliveryMan(@RequestBody DeliveryManUpdateRequestDto requestDto,
            @PathVariable String deliveryManId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(SUCCESS_UPDATE_DELIVERYMAN.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_UPDATE_DELIVERYMAN.getMessage(),hubDeliveryManService.updateDeliveryMan(requestDto,deliveryManId,userDetails)));
    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER)")
    @DeleteMapping("/deliveryMan/{deliveryManId}")
    public ResponseEntity<?  extends CommonResponse> deleteDeliveryMan(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String deliveryManId){

        hubDeliveryManService.deleteDeliveryMan(deliveryManId,userDetails);

        return ResponseEntity.status(SUCCESS_DELETE_DELIVERYMAN.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_DELETE_DELIVERYMAN.getMessage()));
    }

    /***
     * 다음 배송담당자를 조회
     * @return
     */
    @GetMapping("/deliveryMan/next")
    public ResponseEntity<?  extends CommonResponse> findNextDeliveryMan(){

        return ResponseEntity.status(SUCCESS_GET_DELIVERYMAN.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_DELIVERYMAN.getMessage(),hubDeliveryManService.findNextDeliveryMan()));
    }
}
