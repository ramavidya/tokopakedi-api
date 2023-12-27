package com.enigma.tokopakedi.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetails;
}
