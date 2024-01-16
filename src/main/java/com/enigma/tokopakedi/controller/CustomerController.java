package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.repository.CustomerRepository;
import com.enigma.tokopakedi.service.CustomerService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createNewCustomer(@RequestBody Customer customer){
        Customer customer1 = customerService.create(customer);
        WebResponse<Customer> customerWebResponse = WebResponse.<Customer>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Create a customer success")
                .data(customer1)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(customerWebResponse);
    }
    @PostMapping(path = "/customers/bulk")
    public ResponseEntity<?> createManyCustomers(@RequestBody List<Customer> customers){
        List<Customer> customer = customerService.createManyCustomers(customers);
        WebResponse<List<Customer>> customerWebResponse = WebResponse.<List<Customer>>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Create  customers success")
                .data(customer)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(customerWebResponse);

    }
    @GetMapping(path = "/customers")
    public ResponseEntity<?> getAllCustomer(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer size,
                                            @RequestParam(required = false) String name,
                                            @RequestParam(required = false) String phoneNumber)
    {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .page(page)
                .size(size)
                .build();
        Page<Customer> customers = customerService.getAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(size)
                .totalPages(customers.getTotalPages())
                .totalElements(customers.getTotalElements())
                .build();
        WebResponse<List<Customer>> customerWebResponse = WebResponse.<List<Customer>>builder()
                .message("successfully get customers")
                .status(HttpStatus.OK.getReasonPhrase())
                .paging(pagingResponse)
                .data(customers.getContent())
                .build();
        return ResponseEntity.ok(customerWebResponse);
    }
    @GetMapping(path = "/customers/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable("customerId") String customerId){
        Customer customer = customerService.getById(customerId);
        WebResponse<Customer> customerWebResponse = WebResponse.<Customer>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Id found")
                .data(customer)
                .build();
        return ResponseEntity.ok(customerWebResponse);
    }
    @PutMapping(path = "/customers")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
        Customer customer1 = customerService.update(customer);
        CustomerResponse responseData = CustomerResponse.builder()
                .id(customer1.getId())
                .name(customer1.getName())
                .address(customer1.getAddress())
                .phoneNumber(customer1.getPhoneNumber())
                .build();
        WebResponse<CustomerResponse> customerWebResponse = WebResponse.<CustomerResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("updated")
                .data(responseData)
                .build();
        return ResponseEntity.ok(customerWebResponse);
    }
    @DeleteMapping(path = "/customers/{customerId}")
    public ResponseEntity<?> deleteById(@PathVariable String customerId){
        customerService.deleteById(customerId);
        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("Success")
                .data("Deleted")
                .build();
        return ResponseEntity.ok(webResponse);
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
