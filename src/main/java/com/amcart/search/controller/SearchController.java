package com.amcart.search.controller;

import com.amcart.search.model.entity.Products;
import com.amcart.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("/search")
    @Operation(summary = "Load all search", method = "GET")
    public ResponseEntity<Page<Products>> getSearchData(@RequestParam(required = false) String searchTerm,
                                                        @RequestParam(required = false) String categoryId,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize) {
        Page<Products>  toRet = null;
        toRet = productService.searchProducts(searchTerm, categoryId, pageNo, pageSize);
        return new ResponseEntity<>(toRet, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create search data", method = "POST")
    public Products createSearchData(Products products) {
        Products toRet = new Products();
        toRet = productService.createProducts(products);
        return null;
    }
}
