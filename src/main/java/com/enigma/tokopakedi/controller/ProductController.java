package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }
    @PostMapping(path = "/products")
    public Product createNewProduct(@RequestBody Product product){
        return productService.create(product);
    }
    @GetMapping(path = "/products")
    public List<Product> getAllProduct(){
        return productService.getAll();
    }

    @GetMapping(path = "/products/{productId}")
    public Product getProductById(@PathVariable("productId") String productId){
        return productService.getById(productId);
    }

    @PutMapping(path = "/products")
    public Product updateProduct(@RequestBody Product product){
        return productService.update(product);
    }

    @DeleteMapping(path = "/products/{productId}")
    public String deleteById(@PathVariable String productId){
        productService.deleteById(productId);
        return "Delete success";
    }
}
