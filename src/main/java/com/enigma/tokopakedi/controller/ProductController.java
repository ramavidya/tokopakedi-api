package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.model.WebResponse;
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
    public ResponseEntity<WebResponse<Product>> createNewProduct(@RequestBody Product product){
        Product newProduct = productService.create(product);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully create a product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(newProduct)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping(path = "/products/bulk")
    public ResponseEntity<?> createBulk(@RequestBody List<Product> products){
        List<Product> bulk = productService.createBulk(products);
        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("successfully create  products")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(bulk)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping(path = "/products")
    public ResponseEntity<?> getAllProduct(
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
        Page<Product> products = productService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(size)
                .totalPages(products.getTotalPages())
                .totalElements(products.getTotalElements())
                .build();
        WebResponse<List<Product>> response = WebResponse.<List<Product>>builder()
                .message("get all products success")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(products.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") String productId){
        Product byId = productService.getById(productId);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully get a product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(byId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/products")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        Product updated = productService.update(product);
        WebResponse<Product> response = WebResponse.<Product>builder()
                .message("successfully update a product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(updated)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<?> deleteById(@PathVariable String productId){
        productService.deleteById(productId);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("successfully delete a product")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("Deleted")
                .build();
        return ResponseEntity.ok(response);
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
