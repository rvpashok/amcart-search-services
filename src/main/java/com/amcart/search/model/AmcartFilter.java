package com.amcart.search.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties
public class AmcartFilter {
    private String fieldName;
    private Object[] fieldValue;
    private String operator;
}
