package com.indentity.identity.controller;

import com.indentity.identity.dto.request.ApiResponse;
import com.indentity.identity.dto.response.ProductCacheResponse;
import com.indentity.identity.exception.AppException;
import com.indentity.identity.exception.ErrorCode;
import com.indentity.identity.service.ProductCacheService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cache-demo/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCacheController {
    ProductCacheService productCacheService;

    @GetMapping("/{id}")
    ApiResponse<ProductCacheResponse> getProductById(@PathVariable long id) {
        ProductCacheResponse product = productCacheService.getProductById(id);
        if (product == null) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return ApiResponse.<ProductCacheResponse>builder()
                .result(product)
                .build();
    }
}
