package com.amcart.search.service;

import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.request.ProductsSearchRequest;
import com.amcart.search.model.response.ProductsSearchResponse;
import com.amcart.search.model.response.ProductsSuggestionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    ProductsSearchResponse createProductSearchData(ProductsSearchRequest productsSearchRequest);

    ProductsSearchResponse updateProductSearchData(String id, ProductsSearchRequest productsSearchRequest);

    String deleteProductSearchData(String id);

    Page<List<ProductsSearchResponse>> searchProducts(String searchTerm, String categoryId, int pageNo, int pageSize,
                                                List<String> amcartFilter, AmcartSort amcartSort) throws JsonProcessingException;

    Page<ProductsSuggestionResponse> suggestProducts(String searchTerm, String categoryId, int pageNo, int pageSize);
}
