package com.enigma.tokopakedi.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchOrderRequest {
    private Integer page;
    private Integer size;
}
