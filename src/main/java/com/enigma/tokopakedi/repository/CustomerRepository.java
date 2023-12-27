package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer>{
    List<Customer> findByNameContainingOrPhoneNumberContaining(String name, String phoneNumber);

}
