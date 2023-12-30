package com.amcart.search.controller;

import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.entity.Products;
import com.amcart.search.model.request.ProductsSearchRequest;
import com.amcart.search.model.response.ProductsSearchResponse;
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

    @GetMapping("/products")
    @Operation(summary = "Search Products", method = "GET")
    public ResponseEntity<Page<ProductsSearchResponse>> getProductSearchData(@RequestParam(required = false) String searchTerm,
                                                                      @RequestParam(required = false) String categoryId,
                                                                      @RequestParam(required = false, defaultValue = "0") int pageNo,
                                                                      @RequestParam(required = false, defaultValue = "10") int pageSize,
                                                                      @RequestParam(required = false) String amcartFilter,
                                                                      @RequestParam(required = false) String amcartSort) throws JsonProcessingException {
        Page<ProductsSearchResponse> toRet = null;
        AmcartSort amcartSorting = new AmcartSort("price", Sort.Direction.ASC);
        List<String> amcartFiltering = new ArrayList<>();
        if (Objects.nonNull(amcartSort) && !amcartSort.isBlank()) {
            amcartSorting = new ObjectMapper().readValue(amcartSort, AmcartSort.class);
        }
        if (Objects.nonNull(amcartFilter) && !amcartFilter.isBlank()) {
            amcartFiltering = new ObjectMapper().readValue(amcartFilter, List.class);
        }
        toRet = productService.searchProducts(searchTerm, categoryId, pageNo, pageSize, amcartFiltering, amcartSorting);
        return new ResponseEntity<>(toRet, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create search data", method = "POST")
    public ResponseEntity<ProductsSearchResponse> createSearchData(@RequestBody ProductsSearchRequest productsSearchRequest) {
        ProductsSearchResponse toRet = new ProductsSearchResponse();
        toRet = productService.createProductSearchData(productsSearchRequest);
        return new ResponseEntity<>(toRet, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update search data", method = "POST")
    public ResponseEntity<ProductsSearchResponse> updateSearchData(@PathVariable(required = true) String id,
            @RequestBody ProductsSearchRequest productsSearchRequest) {
        ProductsSearchResponse toRet = new ProductsSearchResponse();
        toRet = productService.updateProductSearchData(id, productsSearchRequest);
        return new ResponseEntity<>(toRet, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete search data", method = "POST")
    public ResponseEntity<String> deleteSearchData(@PathVariable(required = true) String id) {
        String toRet = new String();
        toRet = productService.deleteProductSearchData(id);
        return new ResponseEntity<>(toRet, HttpStatus.OK);
    }
}
