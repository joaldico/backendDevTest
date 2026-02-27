package com.itx.test.application.service;

import com.itx.test.domain.model.Product;
import com.itx.test.domain.port.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GetSimilarProductsUseCase {

  private final ProductRepository repository;

  public GetSimilarProductsUseCase(ProductRepository repository) {
    this.repository = repository;
  }

  public List<Product> execute(String productId) {
    List<String> ids = repository.getSimilarIds(productId);

    if (ids == null || ids.isEmpty()) {
      return List.of();
    }

    return ids.parallelStream()
        .map(repository::getProductDetail)
        .filter(Objects::nonNull)
        .toList();
  }
}