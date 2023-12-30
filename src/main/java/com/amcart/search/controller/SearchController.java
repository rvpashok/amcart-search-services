package com.amcart.search.controller;

import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.entity.Products;
import com.amcart.search.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @GetMapping("/products")
    @Operation(summary = "Load all search", method = "GET")
    public ResponseEntity<Page<Products>> getSearchData(@RequestParam(required = false) String searchTerm,
                                                        @RequestParam(required = false) String categoryId,
                                                        @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                        @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                        @RequestParam(required = false) String amcartFilter,
                                                        @RequestParam(required = false) String amcartSort) throws JsonProcessingException {
        Page<Products>  toRet = null;
        AmcartSort amcartSorting = new AmcartSort("price", Sort.Direction.ASC);
        List<String> amcartFiltering = new ArrayList<>();
        if(Objects.nonNull(amcartSort) && !amcartSort.isBlank()){
            amcartSorting = new ObjectMapper().readValue(amcartSort, AmcartSort.class);
        }
        if(Objects.nonNull(amcartFilter) && !amcartFilter.isBlank()){
            amcartFiltering = new ObjectMapper().readValue(amcartFilter, List.class);
        }
        toRet = productService.searchProducts(searchTerm, categoryId, pageNo, pageSize, amcartFiltering, amcartSorting);
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
