package com.domain_expansion.integrity.hub.application.service.deliveryman;

import com.domain_expansion.integrity.hub.common.security.UserDetailsImpl;

public interface HubDeliveryManDeleteService {
    void deleteDeliveryMan(String deliveryManId, UserDetailsImpl userDetails);
}
