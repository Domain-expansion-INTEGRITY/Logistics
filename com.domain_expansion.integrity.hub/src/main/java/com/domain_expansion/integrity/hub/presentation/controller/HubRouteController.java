package com.domain_expansion.integrity.hub.presentation.controller;

import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_CREATE_HUB;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_DELETE_HUBS_ROUTE;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_ALL_HUBS_ROUTE;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_HUB_ROUTE;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_UPDATE_HUB_ROUTE;

import com.domain_expansion.integrity.hub.application.service.hubRoute.HubRouteService;
import com.domain_expansion.integrity.hub.common.response.CommonResponse;
import com.domain_expansion.integrity.hub.common.response.SuccessResponse;
import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteCreateRequestDto;
import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteSearchCondition;
import com.domain_expansion.integrity.hub.presentation.request.hubRoute.HubRouteUpdateRequestDto;
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

@RequestMapping("/api/v1/hubs/routes")
@RestController
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteService hubRouteService;

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER)")
    @PostMapping
    public ResponseEntity<?  extends CommonResponse> createHubRoute(@RequestBody HubRouteCreateRequestDto requestDto){

        return ResponseEntity.status(SUCCESS_CREATE_HUB.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_CREATE_HUB.getMessage(),hubRouteService.createHubRoute(requestDto)));
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<?  extends CommonResponse> getHubRoute(@PathVariable String routeId){

        return ResponseEntity.status(SUCCESS_GET_HUB_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_HUB_ROUTE.getMessage(),hubRouteService.getHubRoute(routeId)));
    }

    @GetMapping
    public ResponseEntity<?  extends CommonResponse> getAllHubRoutes(@ModelAttribute
            HubRouteSearchCondition searchCondition, @PageableDefault(size = 10) Pageable pageable){

        return ResponseEntity.status(SUCCESS_GET_ALL_HUBS_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_ALL_HUBS_ROUTE.getMessage(),hubRouteService.getHubRoutes(searchCondition, pageable)));
    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER)")
    @PatchMapping("/{routeId}")
    public ResponseEntity<?  extends CommonResponse> updateHubRoute(@RequestBody HubRouteUpdateRequestDto requestDto,
            @PathVariable String routeId){

        return ResponseEntity.status(SUCCESS_UPDATE_HUB_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_UPDATE_HUB_ROUTE.getMessage(),hubRouteService.updateHubRoute(requestDto,routeId)));
    }

    @PreAuthorize("hasAnyRole(T(com.domain_expansion.integrity.hub.application.shared.RoleConstants).ROLE_MASTER)")
    @DeleteMapping("/{routeId}")
    public ResponseEntity<?  extends CommonResponse> deleteHubRoute(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String routeId){

        hubRouteService.deleteHubRoute(routeId,userDetails);

        return ResponseEntity.status(SUCCESS_DELETE_HUBS_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_DELETE_HUBS_ROUTE.getMessage()));
    }

    @GetMapping(params = {"startHubId","endHubId"})
    public ResponseEntity<?  extends CommonResponse> getRouteFromStartToEnd(@RequestParam("startHubId") String startHubId,
            @RequestParam("endHubId") String endHubId){

        return ResponseEntity.status(SUCCESS_GET_HUB_ROUTE.getHttpStatus())
                .body(SuccessResponse.success(SUCCESS_GET_HUB_ROUTE.getMessage(),hubRouteService.getRouteFromStartToEnd(startHubId,endHubId)));
    }
}
