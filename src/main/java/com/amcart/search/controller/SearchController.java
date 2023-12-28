package com.amcart.search.controller;

import com.amcart.search.model.entity.Products;
import com.amcart.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService productService;

    @GetMapping
    @Operation(summary = "Load all search", method = "GET")
    public Iterable<Products> getAllSearchData() {
        Iterable<Products> toRet = new ArrayList<>();
        toRet = productService.loadAllProducts();
        return toRet;
    }

    @PostMapping
    @Operation(summary = "Create search data", method = "POST")
    public Products createSearchData(Products products) {
        Products toRet = new Products();
        toRet = productService.createProducts(products);
        return null;
    }
}
