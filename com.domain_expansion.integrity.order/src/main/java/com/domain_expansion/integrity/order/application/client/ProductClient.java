package com.domain_expansion.integrity.order.application.client;

import com.domain_expansion.integrity.order.application.client.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/v1/products/{productId}")
    ResponseEntity<ProductResponse> getProductById(@PathVariable("productId") String productId);

}
