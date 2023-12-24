package com.amcart.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {
    String id;
    String productId;
    PriceType priceType;
    CurrencyType currencyType;
    String skuId;
    double price;
    boolean isDeleted;
    Map<String, Object> properties;
    long createdOn;
    long updatedOn;
    String createdBy;
    String updatedBy;
}
