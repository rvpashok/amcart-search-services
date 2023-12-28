package com.amcart.search.service.impl;

import com.amcart.search.model.entity.Products;
import com.amcart.search.repository.SearchRepository;
import com.amcart.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;
    @Override
    public Iterable<Products> loadAllProducts() {
        Iterable<Products> toRet = searchRepository.findAll();
        return toRet;
    }

    @Override
    public Products createProducts(Products products) {
        Products toRet = searchRepository.save(products);
        return toRet;
    }
}
