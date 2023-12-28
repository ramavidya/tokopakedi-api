package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.*;
import com.enigma.tokopakedi.repository.OrderRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.OrderDetailService;
import com.enigma.tokopakedi.service.OrderService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final CustomerService customerService;
    private final ProductService productService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse createTransaction(OrderRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Order order = Order.builder()
                .customer(customer)
                .transDate(new Date())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()){
            Product product = productService.getById(orderDetailRequest.getProductId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .productPrice(product.getPrice())
                    .quantity(orderDetailRequest.getQuantity())
                    .build();

            if (product.getStock() - orderDetailRequest.getQuantity() < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceeds the stocks");
            }

            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
                    .id(orderDetail.getId())
                    .orderId(order.getId())
                    .product(product)
                    .productPrice(orderDetail.getProductPrice())
                    .quantity(orderDetail.getQuantity())
                    .build();

            product.setStock(product.getStock() - orderDetailRequest.getQuantity());
            orderDetailService.createOrUpdate(orderDetail);
            productService.update(product);

            orderDetails.add(orderDetailResponse);


        }
        OrderResponse orderResponse = OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomer().getId())
                .transDate(order.getTransDate())
                .orderDetails(orderDetails)
                .build();


        return orderResponse;
    }

    @Override
    public Order getById(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "not found"));
        return order;

    }

    @Override
    public Page<Order> getAll(SearchOrderRequest request) {
        if (request.getPage() <= 0) request.setPage(1);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        return orderRepository.findAll(pageable);

//        List<OrderDetailResponse> orderDetails = new ArrayList<>();
//
//        for (OrderDetailResponse orderDetailRequest : orders.getContent()) {
//            Product product = productService.getById(orderDetailRequest.getProduct().getId());
//
//            OrderDetail orderDetail = OrderDetail.builder()
//                    .order(orderService.getById(orderDetailRequest.getId()))
//                    .product(product)
//                    .productPrice(product.getPrice())
//                    .quantity(orderDetailRequest.getQuantity())
//                    .build();
//
//            OrderDetailResponse orderDetailResponse = OrderDetailResponse.builder()
//                    .id(orderDetail.getId())
//                    .orderId(orderDetailRequest.getOrderId())
//                    .product(product)
//                    .productPrice(orderDetail.getProductPrice())
//                    .quantity(orderDetail.getQuantity())
//                    .build();
//
//        OrderResponse orderResponse = OrderResponse.builder()
//                .id(orders.getId())
//                .customerId(orderDetailResponse.getId())
//                .transDate(orderDetail.getOrder().getTransDate())
//                .orderDetails(orderDetails)
//                .build();
//
//
//            orderDetails.add(orderDetailResponse);
//            WebResponse<OrderResponse> response = WebResponse.<OrderResponse>builder()
//                    .status(HttpStatus.OK.getReasonPhrase())
//                    .message("successfully get transactions")
//                    .paging(pagingResponse)
//                    .data(orderResponse)
//                    .build()
//        }
//        return orderRepository.findAll(orderDetails, pageable);
    }
}
