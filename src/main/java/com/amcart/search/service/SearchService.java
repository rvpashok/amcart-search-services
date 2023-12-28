package com.amcart.search.service;

import com.amcart.search.model.entity.Products;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    Iterable<Products> loadAllProducts();

    Products createProducts(Products products);

    public List<Products>  searchProducts(String searchTerm);
}
