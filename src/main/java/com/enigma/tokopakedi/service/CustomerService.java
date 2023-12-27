package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.SearchCustomerRequest;
import com.enigma.tokopakedi.model.SearchProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    void deleteById(String customerId);
    Customer create(Customer customer);
    List<Customer> createManyCustomers(List<Customer> customers);
    Page<Customer> getAll(SearchCustomerRequest request);
    Customer getById(String customerId);
    Customer update(Customer customer);
    List<Customer> searchByNameOrPhoneNumber(String name, String phoneNumber);

}
