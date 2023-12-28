package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.service.OrderService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createNewTransaction(@RequestBody OrderRequest request) {

        OrderResponse orderResponse = orderService.createTransaction(request);

        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Create a transaction success")
                .data(orderResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        Order order = orderService.getById(id);
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .transDate(order.getTransDate())
                .orderDetails(order.getOrderDetails())
                .build();
//        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("successfully get transaction by id")
//                .data(orderResponse)
//                .build();
        WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully get transaction by id")
                .data(orderResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllTransaction(@RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        SearchOrderRequest request = SearchOrderRequest.builder()
                .page(page)
                .size(size)
                .build();
        Page<Order> orders = orderService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(orders.getTotalPages())
                .totalElements(orders.getTotalElements())
                .build();

//        OrderDetailResponse orderDetail = OrderDetail.builder()
//                .
//                .build();
//
//        WebResponse<List<OrderResponse>> response = WebResponse.<List<OrderResponse>>builder()
//                .status(HttpStatus.OK.getReasonPhrase())
//                .message("successfully get all transaction")
//                .data()
//                .paging(pagingResponse)
//                .build();

        WebResponse<List<Order>> response = WebResponse.<List<Order>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .message("successfully get all transaction")
                .data(orders.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);


        }

}