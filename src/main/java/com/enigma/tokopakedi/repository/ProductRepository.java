package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {
    List<Product> findByNameContainingOrPriceLessThanEqualOrPriceGreaterThanEqual(String name, Integer minPrice, Integer maxPrice);
    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);

}
