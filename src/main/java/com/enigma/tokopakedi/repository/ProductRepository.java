package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}
