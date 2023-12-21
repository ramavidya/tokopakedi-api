package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    private final CustomerService customerService;

    public CustomerController(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @PostMapping(path = "/customers")
    public Customer createNewCustomer(@RequestBody Customer customer){
        return customerService.create(customer);
    }
    @GetMapping(path = "/customers")
    public List<Customer> getAllCustomer(){
        return customerService.getAll();
    }

    @GetMapping(path = "/customers/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") String customerId){
        return customerService.getById(customerId);

    }

    @PutMapping(path = "/customers")
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.update(customer);
    }
    @DeleteMapping(path = "/customers/{customerId}")
    public String deleteById(@PathVariable String customerId){
        customerService.deleteById(customerId);
        return "Delete success";
    }

}
