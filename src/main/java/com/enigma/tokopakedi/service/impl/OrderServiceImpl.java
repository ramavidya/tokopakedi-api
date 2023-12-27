package com.enigma.tokopakedi.service.impl;

import com.enigma.tokopakedi.entity.Customer;
import com.enigma.tokopakedi.entity.Order;
import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.entity.Product;
import com.enigma.tokopakedi.model.OrderDetailRequest;
import com.enigma.tokopakedi.model.OrderRequest;
import com.enigma.tokopakedi.repository.OrderRepository;
import com.enigma.tokopakedi.service.CustomerService;
import com.enigma.tokopakedi.service.OrderDetailService;
import com.enigma.tokopakedi.service.OrderService;
import com.enigma.tokopakedi.service.ProductService;
import lombok.RequiredArgsConstructor;
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
    public Order createTransaction(OrderRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Order order = Order.builder()
                .customer(customer)
                .transDate(new Date())
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetail> orderDetails = new ArrayList<>();

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

            product.setStock(product.getStock() - orderDetailRequest.getQuantity());
            productService.update(product);

            orderDetails.add(orderDetail);

        }
        order.setOrderDetails(orderDetails);

        return order;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Order createTransaction(OrderRequest request) {
//        Customer customer = customerService.getById(request.getCustomerId());
//        Order order = Order.builder()
//                .customer(customer)
//                .transDate(new Date())
//                .build();
//        orderRepository.saveAndFlush(order);
//
//        List<OrderDetail> orderDetails = new ArrayList<>();
//        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()){
//            Product product = productService.getById(orderDetailRequest.getProductId());
//
//            OrderDetail orderDetail = OrderDetail.builder()
//                    .order(order)
//                    .product(product)
//                    .productPrice(product.getPrice())
//                    .quantity(orderDetailRequest.getQuantity())
//                    .build();
//
//            if (product.getStock() - orderDetailRequest.getQuantity() < 0){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity exceeds the stocks");
//            }
//
//            product.setStock(product.getStock() - orderDetailRequest.getQuantity());
//
//            productService.update(product);
//
//            orderDetails.add(orderDetailService.createOrUpdate(orderDetail));
//
//        }
//        order.setOrderDetails(orderDetails);
//
//        return order;
//    }
}
