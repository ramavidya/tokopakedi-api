package com.enigma.tokopakedi.model;

import com.enigma.tokopakedi.entity.OrderDetail;
import com.enigma.tokopakedi.entity.Product;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse<T> {
    private String id;
    private String customerId;
    private Date transDate;
    private T orderDetails;
}
