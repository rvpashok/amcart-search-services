package com.amcart.search.service.impl;

import com.amcart.search.model.ProductResponse;
import com.amcart.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchServiceImpl implements SearchService {

    @Override
    public List<ProductResponse> loadAllProducts() {
        return null;
    }
}
