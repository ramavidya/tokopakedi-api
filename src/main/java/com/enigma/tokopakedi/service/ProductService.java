package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Product;

import java.util.List;

public interface ProductService {
    void deleteById(String productId);
    Product create(Product product);
    List<Product> getAll();
    Product getById(String productId);
    Product update(Product product);

}
