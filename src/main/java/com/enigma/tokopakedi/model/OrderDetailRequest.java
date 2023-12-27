package com.enigma.tokopakedi.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {
    private String productId;
    private Integer quantity;
}
