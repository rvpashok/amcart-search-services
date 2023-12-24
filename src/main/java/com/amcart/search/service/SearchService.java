package com.amcart.search.service;

import com.amcart.search.model.ProductRequest;
import com.amcart.search.model.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    List<ProductResponse> loadAllProducts();
}
