package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.model.OrderDetailResponse;
import com.enigma.tokopakedi.model.OrderRequest;
import com.enigma.tokopakedi.model.OrderResponse;
import com.enigma.tokopakedi.model.SearchOrderRequest;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse createTransaction(OrderRequest request);
    Order getById(String id);

    Page<Order> getAll(SearchOrderRequest request);
}
