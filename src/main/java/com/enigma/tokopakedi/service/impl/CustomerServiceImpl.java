package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.model.SearchProductRequest;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Customer> getAll(Pageable pageable) {

        return customerRepository.findAll(pageable);
//        @Override
//        public Page<Customer> getCustomersPaging(Integer pageNumber, Integer pageSize) {
//            Pageable pageable = PageRequest.of(pageNumber, pageSize);
//            return customerRepository.findAll(pageable);
//        }
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
