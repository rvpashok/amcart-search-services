package com.amcart.search.model.response;

import com.amcart.search.model.CurrencyType;
import com.amcart.search.model.PriceType;
import com.amcart.search.model.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsSearchResponse {
    String id;
    String productId;
    String name;
    String shortDescription;
    String longDescription;
    String additionalInfo;
    String brand;
    ProductStatus status;
    List<String> tags;
    String skuId;
    String skuName;
    String skuColor;
    String skuMediaUrl;
    String skuSize;
    List<String> categoryIds;
    PriceType priceType;
    CurrencyType currencyType;
    double price;
    double originalPrice;
    boolean isDeleted;
    Map<String, Object> properties;
}
