package com.amcart.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class PriceRequest {
    String skuId;
    PriceType priceType;
    CurrencyType currencyType;
    double price;
    boolean isDeleted;
    Map<String, Object> properties;
}
