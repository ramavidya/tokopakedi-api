package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.model.OrderRequest;
import com.enigma.tokopakedi.model.WebResponse;
import com.enigma.tokopakedi.service.OrderService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping(path = "/orders")
    public ResponseEntity<?> createNewTransaction(@RequestBody OrderRequest request){
        Order transaction = orderService.createTransaction(request);
        WebResponse<Order> response = WebResponse.<Order>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Create a transcaction success")
                .data(transaction)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
