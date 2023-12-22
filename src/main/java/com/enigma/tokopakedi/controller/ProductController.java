package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product){
        Product newProduct = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }
    @PostMapping(path = "/products/bulk")
    public ResponseEntity<List<Product>> createBulk(@RequestBody List<Product> products){
        List<Product> bulk = productService.createBulk(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(bulk);
    }
    @GetMapping(path = "/products")
    public ResponseEntity<Page<Product>> getAllProduct(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice
    ){
        SearchProductRequest request = SearchProductRequest.builder()
                .page(page)
                .size(size)
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        Page<Product> all = productService.getAll(request);
        return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId){
        Product byId = productService.getById(productId);
        return ResponseEntity.ok(byId);
    }

    @PutMapping(path = "/products")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        Product updated = productService.update(product);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<String> deleteById(@PathVariable String productId){
        productService.deleteById(productId);
        return ResponseEntity.ok("Ok");
    }

    @GetMapping(path = "/products/search")
    public List<Product> productsSearchByNameAndPrice(@RequestParam(required = false)String name,
                                                      @RequestParam(required = false)Integer minPrice,
                                                      @RequestParam(required = false)Integer maxPrice){
        return productService.getProductNameOrPriceRange(name, minPrice,maxPrice);
    }
    @GetMapping(path = "/products/searchPrice")
    public List<Product> productsSearchByPrice(
                                                      @RequestParam(required = false)Integer minPrice,
                                                      @RequestParam(required = false)Integer maxPrice){
        return productService.getProductByPriceRange(minPrice,maxPrice);
    }


}
