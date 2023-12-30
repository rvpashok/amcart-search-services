package com.amcart.search.service;

import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.entity.Products;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    Products createProducts(Products products);

    Page<Products> searchProducts(String searchTerm, String categoryId, int pageNo, int pageSize,
                                         List<String> amcartFilter, AmcartSort amcartSort) throws JsonProcessingException;
}
