package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @PostMapping(path = "/products")
    public Product createNewProduct(@RequestBody Product product){
        Product newProduct = productRepository.save(product);
        return newProduct;
    }
    @GetMapping(path = "/products")
    public List<Product> getAllProduct(){
        List<Product> products = productRepository.findAll();
        return products;
    }
    @DeleteMapping(path = "/products/{productId}")
    public String delete(@PathVariable String productId){
        Product product = new Product();
        product.setId(productId);
        productRepository.deleteById(productId);
        return "Ok";
    }

    @GetMapping(path = "/products/{productId}")
    public Product getProductById(@PathVariable("productId") String productId){
        Product product = productRepository.findById(productId).orElseThrow();
        return product;
    }

    @PutMapping(path = "/products/{productId}")
    public Product update(@PathVariable("productId") String productId, @RequestBody Product product){
        product.setId(productId);
        productRepository.save(product);
        return product;
    }
}
