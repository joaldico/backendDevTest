package com.itx.test.infrastructure.rest;

import com.itx.test.application.service.GetSimilarProductsUseCase;
import com.itx.test.domain.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final GetSimilarProductsUseCase useCase;

    public ProductController(GetSimilarProductsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/{productId}/similar")
    public ResponseEntity<List<Product>> getSimilarProducts(@PathVariable String productId) {
        log.info("Recibida petición para obtener productos similares al ID: {}", productId);
        
        List<Product> similarProducts = useCase.execute(productId);
        
        return ResponseEntity.ok(similarProducts);
    }
}