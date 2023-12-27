package com.enigma.tokopakedi.model;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCustomerRequest {
    private Integer page;
    private Integer size;
    private String name;
    private String phoneNumber;
}
