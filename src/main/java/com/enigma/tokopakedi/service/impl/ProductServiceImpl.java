package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.repository.ProductRepository;
import com.enigma.tokopakedi.service.ProductService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteById(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found");
        Product product = optionalProduct.get();
        productRepository.delete(product);
    }
    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }
    @Override
    public Page<Product> getAll(SearchProductRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Specification<Product> specification = getProductSpecification(request);
        return productRepository.findAll(specification, pageable);
    }
    @Override
    public List<Product> createBulk(List<Product> products) {
        return productRepository.saveAll(products);
    }
    @Override
    public Product getById(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) return optionalProduct.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found");

    }
    @Override
    public Product update(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        if (optionalProduct.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"product not found");
        return productRepository.save(product);
    }
    @Override
    public List<Product> getProductNameOrPriceRange(String name, Integer minPrice, Integer maxPrice) {
        return productRepository.findByNameContainingOrPriceLessThanEqualOrPriceGreaterThanEqual(name, minPrice, maxPrice);
    }
    @Override
    public List<Product> getProductByPriceRange(Integer minPrice, Integer maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    private  Specification<Product> getProductSpecification(SearchProductRequest request) {
        Specification<Product> specification = ((root, query, criteriaBuilder) -> {
            //root -> from table m_product
            //query -> where, from, select, distinct, groupBy, having
            //criteriaBuilder -> select, update, delete
            //operasi -> (=, <=, >=, !=),
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null){
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%");
                predicates.add(namePredicate);
            }
            if (request.getMinPrice() != null){
                Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("price"), request.getMinPrice());
                predicates.add(minPricePredicate);
            }
            if (request.getMaxPrice() != null){
                Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice());
                predicates.add(maxPricePredicate);
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
        return specification;
    }
}


