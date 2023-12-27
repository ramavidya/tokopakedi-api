package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.PagingResponse;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void deleteById(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) throw new RuntimeException("customer not found");
        Customer customer = optionalCustomer.get();
        customerRepository.delete(customer);
    }

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> createManyCustomers(List<Customer> customers) {

        return customerRepository.saveAll(customers);
    }

    @Override
    public Page<Customer> getAll(SearchCustomerRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize());
        Specification<Customer> specification = getSpecification(request);

        return customerRepository.findAll(specification, pageable);
    }
    private Specification<Customer> getSpecification(SearchCustomerRequest request){
        Specification<Customer> customerSpecification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null){
                Predicate namePredicate = criteriaBuilder.like(root.get("name"),"%" + request.getName() + "%");
                predicates.add(namePredicate);
            }
            if (request.getPhoneNumber() != null){
                Predicate phoneNumber = criteriaBuilder.equal(root.get("phoneNumber"), request.getPhoneNumber());
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });
        return customerSpecification;
    }

    @Override
    public Customer getById(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) throw new RuntimeException("Customer not found");
        return optionalCustomer.get();
    }

    @Override
    public Customer update(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isEmpty()) throw new RuntimeException("customer not found");
        return customerRepository.save(customer);
    }



    @Override
    public List<Customer> searchByNameOrPhoneNumber(String name, String phoneNumber) {
        return customerRepository.findByNameContainingOrPhoneNumberContaining(name, phoneNumber);
    }




}
