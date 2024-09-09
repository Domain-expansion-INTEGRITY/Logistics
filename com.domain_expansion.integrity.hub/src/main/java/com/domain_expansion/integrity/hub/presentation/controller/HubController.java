package com.domain_expansion.integrity.hub.presentation.controller;

import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_CREATE_HUB;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_DELETE_HUBS;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_ALL_HUBS;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_GET_HUB;
import static com.domain_expansion.integrity.hub.common.message.SuccessMessage.SUCCESS_UPDATE_HUB;

import com.domain_expansion.integrity.hub.application.service.HubService;
import com.domain_expansion.integrity.hub.common.response.SuccessResponse;
import com.domain_expansion.integrity.hub.presentation.request.HubCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/hubs")
@RestController
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping
    public SuccessResponse<?> createHub(@RequestBody HubCreateRequestDto requestDto){

        return SuccessResponse.success(SUCCESS_CREATE_HUB.getMessage());
    }

    @GetMapping("/{hub_id}")
    public SuccessResponse<?> getHub(@PathVariable String hub_id){
        return SuccessResponse.success(SUCCESS_GET_HUB.getMessage());
    }

    @GetMapping
    public SuccessResponse<?> getAllHubs(@PageableDefault(size = 10) Pageable pageable){
        return SuccessResponse.success(SUCCESS_GET_ALL_HUBS.getMessage());
    }

    @PatchMapping("/{hub_id}")
    public SuccessResponse<?> updateHub(@PathVariable String hub_id){
        return SuccessResponse.success(SUCCESS_UPDATE_HUB.getMessage());
    }

    @DeleteMapping("/{hub_id}")
    public SuccessResponse<?> deleteHub(@PathVariable String hub_id){
        return SuccessResponse.success(SUCCESS_DELETE_HUBS.getMessage());
    }

}
