package com.itx.test.infrastructure.adapter;

import com.itx.test.domain.model.Product;
import com.itx.test.domain.port.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;

@Slf4j 
@Component
public class ProductHttpClient implements ProductRepository {

    private final RestClient restClient;

    public ProductHttpClient(@Value("${external.mocks.base-url}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    @CircuitBreaker(name = "productMocks", fallbackMethod = "fallbackSimilarIds")
    public List<String> getSimilarIds(String productId) {
        return restClient.get()
                .uri("/product/{productId}/similarids", productId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<String>>() {});
    }

    @Override
    @CircuitBreaker(name = "productMocks", fallbackMethod = "fallbackProductDetail")
    public Product getProductDetail(String productId) {
        return restClient.get()
                .uri("/product/{productId}", productId)
                .retrieve()
                .body(Product.class);
    }

    public List<String> fallbackSimilarIds(String productId, Throwable t) {
        log.error("Error obteniendo IDs similares para el producto {}: {}", productId, t.getMessage());
        return Collections.emptyList(); 
    }

    public Product fallbackProductDetail(String productId, Throwable t) {
        log.error("Error obteniendo detalle del producto {}: {}", productId, t.getMessage());
        return null; 
    }
}