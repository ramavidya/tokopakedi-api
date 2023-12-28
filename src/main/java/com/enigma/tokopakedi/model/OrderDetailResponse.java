package com.enigma.tokopakedi.model;

import com.enigma.tokopakedi.entity.Product;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private String id;
    private String orderId;
    private Product product;
    private Long productPrice;
    private Integer quantity;
}
