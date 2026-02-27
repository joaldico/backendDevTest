package com.itx.test.domain.port;

import com.itx.test.domain.model.Product;
import java.util.List;

public interface ProductRepository {
    List<String> getSimilarIds(String productId);
    Product getProductDetail(String productId);
}