package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void deleteById(String productId);
    Product create(Product product);
    Page<Product> getAll(SearchProductRequest request);
    List<Product> createBulk(List<Product> products);
    Product getById(String productId);
    Product update(Product product);

    List<Product> getProductNameOrPriceRange(String name, Integer minPrice, Integer maxPrice);

    List<Product> getProductByPriceRange(Integer minPrice, Integer maxPrice);
}
