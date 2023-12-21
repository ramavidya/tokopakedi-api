package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Customer;

import java.util.List;

public interface CustomerService {
    void deleteById(String customerId);
    Customer create(Customer customer);
    List<Customer> getAll();
    Customer getById(String customerId);
    Customer update(Customer customer);

}
