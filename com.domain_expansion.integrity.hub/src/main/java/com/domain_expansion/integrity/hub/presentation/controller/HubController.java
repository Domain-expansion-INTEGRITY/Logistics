package com.domain_expansion.integrity.hub.presentation.controller;

import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.*;

import com.domain_expansion.integrity.hub.common.response.SuccessResponse;
import com.domain_expansion.integrity.hub.common.response.CommonResponse;
import com.domain_expansion.integrity.hub.application.service.HubService;
import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.HubSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.HubUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class HubController {

    private final HubService hubService;

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER)")
    @PostMapping
    public ResponseEntity<?  extends CommonResponse> createHub(@RequestBody HubCreateRequestDto requestDto){

        return ResponseEntity.status(SUCCESS_CREATE_HUB.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_CREATE_HUB.getMessage(), hubService.createHub(requestDto)));
    }

    @GetMapping("/{hub_id}")
    public ResponseEntity<?  extends CommonResponse>  getHub(@PathVariable String hub_id){
        return ResponseEntity.status(SUCCESS_GET_HUB.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_HUB.getMessage(), hubService.getHubById(hub_id)));

    }

    @GetMapping
    public ResponseEntity<?  extends CommonResponse>  getAllHubs(
            @ModelAttribute HubSearchCondition searchCondition,
            @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.status(SUCCESS_GET_ALL_HUBS.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_ALL_HUBS.getMessage(),hubService.getAllHubs(searchCondition,pageable)));

    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER)")
    @PatchMapping("/{hub_id}")
    public ResponseEntity<?  extends CommonResponse> updateHub(@PathVariable HubUpdateRequestDto requestDto){
        return ResponseEntity.status(SUCCESS_UPDATE_HUB.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_UPDATE_HUB.getMessage(), hubService.updateHub(requestDto)));

    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.company.application.shared.RoleConstants).ROLE_MASTER)")
    @DeleteMapping("/{hub_id}")
    public ResponseEntity<?  extends CommonResponse>  deleteHub(@PathVariable String hub_id){

        hubService.deleteHub(hub_id);

        return ResponseEntity.status(SUCCESS_UPDATE_HUB.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_UPDATE_HUB.getMessage()));

    }

    @GetMapping("/{hub_id}/validate")
    public ResponseEntity<?  extends CommonResponse>  validateHub(@PathVariable String hub_id,
            @RequestParam Long userId){
        return ResponseEntity.status(SUCCESS_GET_HUB.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_HUB.getMessage()));

    }

}
