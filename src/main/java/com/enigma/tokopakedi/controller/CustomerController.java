package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping(path = "/customers")
    public Customer createNewCustomer(@RequestBody Customer customer){
        Customer customer1 = customerRepository.save(customer);
        return customer1;
    }
    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomer(){
        List<Customer> customers = customerRepository.findAll();
        return customers;
    }
    @DeleteMapping(path = "/customers/{customerId}")
    public String delete(@PathVariable String customerId){
        Customer customer = new Customer();
        customer.setId(customerId);
        customerRepository.deleteById(customerId);
        return "Ok";
    }

    @GetMapping(path = "/customers/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") String customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        return customer;
    }

    @PutMapping(path = "/customers/{customerId}")
    public Customer update(@PathVariable("customerId") String customerId, @RequestBody Customer customer){
        customer.setId(customerId);
        customerRepository.save(customer);
        return customer;
    }


}
