package com.amcart.search.model.request;

import com.amcart.search.model.CurrencyType;
import com.amcart.search.model.PriceType;
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
    double originalPrice;
    boolean isDeleted;
    Map<String, Object> properties;
}
