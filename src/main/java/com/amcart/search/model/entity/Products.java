package com.amcart.search.model.entity;

import com.amcart.search.model.CurrencyType;
import com.amcart.search.model.PriceType;
import com.amcart.search.model.ProductStatus;
import com.amcart.search.model.response.ProductsSearchResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
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
    String skuId;
    String skuName;
    String skuColor;
    String skuMediaUrl;
    String skuSize;
    List<String> categoryIds;
    PriceType priceType;
    CurrencyType currencyType;
    double price;
    boolean isDeleted;
    Map<String, Object> properties;

    public ProductsSearchResponse convertToReponseModel(){
        ProductsSearchResponse toRet = new ProductsSearchResponse();
        toRet.setId(this.getId());
        toRet.setProductId(this.getProductId());
        toRet.setName(this.getName());
        toRet.setShortDescription(this.getShortDescription());
        toRet.setLongDescription(this.getLongDescription());
        toRet.setAdditionalInfo(this.getAdditionalInfo());
        toRet.setBrand(this.getBrand());
        toRet.setStatus(this.getStatus());
        toRet.setTags(this.getTags());
        toRet.setSkuId(this.getSkuId());
        toRet.setSkuName(this.getSkuName());
        toRet.setSkuColor(this.getSkuColor());
        toRet.setSkuMediaUrl(this.getSkuMediaUrl());
        toRet.setSkuSize(this.getSkuSize());
        toRet.setCategoryIds(this.getCategoryIds());
        toRet.setPriceType(this.getPriceType());
        toRet.setCurrencyType(this.getCurrencyType());
        toRet.setPrice(this.getPrice());
        toRet.setProperties(this.getProperties());
        toRet.setDeleted(this.isDeleted());
        return toRet;
    }
}
