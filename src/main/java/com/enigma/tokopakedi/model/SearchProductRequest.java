package com.enigma.tokopakedi.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductRequest {
     private Integer page;

     private Integer size;

     private String name;

     private Long minPrice;

     private Long maxPrice;
}
