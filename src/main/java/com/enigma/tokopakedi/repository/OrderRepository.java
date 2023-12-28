package com.enigma.tokopakedi.repository;

import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.model.OrderDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    //Page<OrderDetailResponse> findAll(List<OrderDetailResponse> orderDetails, Pageable pageable);
}
