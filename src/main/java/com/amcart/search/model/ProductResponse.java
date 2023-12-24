package com.amcart.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
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
    long createdOn;
    long updatedOn;
    String createdBy;
    String updatedBy;
}
