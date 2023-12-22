package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Customer> getAllCustomer(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size)
    {
        if (page <= 0) page = 0;
        Pageable pageable = PageRequest.of(page - 1,size);
        return customerService.getAll(pageable);
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
//    @GetMapping(path = "/customers/{pageNumber}/{pageSize}")
//    public Page<Customer> customerPaging(@PathVariable int pageNumber, @PathVariable int pageSize){
//        return customerService.getCustomersPaging(pageNumber, pageSize);
//    }
    @GetMapping(path = "/customers/search")
    public List<Customer> searchByNameOrPhoneNumber(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) String phoneNumber){
        return customerService.searchByNameOrPhoneNumber(name, phoneNumber);
    }


}
