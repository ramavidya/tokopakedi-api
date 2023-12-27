package com.enigma.tokopakedi.service;

import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.model.OrderRequest;

public interface OrderService {
    Order createTransaction(OrderRequest request);
}
