package com.amcart.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AmcartSort {
    private String fieldName;
    private org.springframework.data.domain.Sort.Direction direction;
}
