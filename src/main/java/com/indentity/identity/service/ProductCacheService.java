package com.indentity.identity.service;

import com.indentity.identity.dto.response.ProductCacheResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductCacheService {
    CacheManager cacheManager;

    // Giả lập bảng lưu trữ dữ liệu trong Database
    private static final Map<Long, ProductCacheResponse> MOCK_DATABASE = new ConcurrentHashMap<>();

    static {
        MOCK_DATABASE.put(101L, new ProductCacheResponse(
                101L, "MacBook Pro M3 Max 16-inch", new BigDecimal("79990000"),
                "Apple M3 Max chip with 16-core CPU and 40-core GPU", 25,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ));
        MOCK_DATABASE.put(102L, new ProductCacheResponse(
                102L, "iPhone 16 Pro Max 256GB", new BigDecimal("34990000"),
                "Titanium frame with A18 Pro Bionic Chip", 50,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ));
        MOCK_DATABASE.put(103L, new ProductCacheResponse(
                103L, "Sony WH-1000XM5 Wireless Headphones", new BigDecimal("8490000"),
                "Industry-leading noise canceling headphones with Auto NC Optimizer", 40,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ));
    }

    @Cacheable(value = "product_detail", key = "#id", unless = "#result == null")
    public ProductCacheResponse getProductById(long id) {
        log.info("[CACHE MISS] Đang truy vấn Database cho Product ID: {}...", id);

        // Giả lập độ trễ truy vấn Database (200ms delay cho SQL Query / Index Scan / Join)
        simulateDatabaseDelay(200);

        ProductCacheResponse product = MOCK_DATABASE.get(id);
        if (product == null) {
            log.warn("Không tìm thấy Product ID: {} trong Database", id);
            return null;
        }

        // Tạo bản ghi mới cập nhật lại thời điểm được nạp vào Cache
        return new ProductCacheResponse(
                product.id(), product.name(), product.price(),
                product.description(), product.stock(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    private void simulateDatabaseDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
