package com.amcart.search.model.entity;

import com.amcart.search.model.CurrencyType;
import com.amcart.search.model.PriceType;
import com.amcart.search.model.ProductStatus;
import com.amcart.search.model.Sku;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "search-entity")
public class Products {
    @Id
    String id;
    String productId;
    String name;
    String shortDescription;
    String longDescription;
    String additionalInfo;
    String brand;
    ProductStatus status;
    List<String> tags;
    List<Sku> skus;
    boolean isDeleted;
    List<String> categoryIds;
    Map<String, Object> properties;
    String skuId;
    PriceType priceType;
    CurrencyType currencyType;
    double price;
}
