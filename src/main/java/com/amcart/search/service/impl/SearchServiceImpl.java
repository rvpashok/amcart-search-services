package com.amcart.search.service.impl;

import com.amcart.search.model.entity.Products;
import com.amcart.search.repository.SearchRepository;
import com.amcart.search.service.SearchService;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.CriteriaQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Iterable<Products> loadAllProducts() {
        Iterable<Products> toRet = searchRepository.findAll();
        return toRet;
    }

    @Override
    public List<Products> searchProducts(String searchTerm) {
        List<Products> toRet = new ArrayList<>();
        Criteria criteria = new Criteria("brand");
        criteria.matches(searchTerm);
        CriteriaQueryBuilder criteriaQueryBuilder = CriteriaQuery.builder(criteria);
        Query query = new CriteriaQuery(criteriaQueryBuilder);
        SearchHits<Products> results = elasticsearchOperations.search(query, Products.class);
        if(results != null && !results.isEmpty()){
            results.getSearchHits().stream().forEach(f->{
                Products products = f.getContent();
                toRet.add(products);
            });
        }
        return toRet;
    }

    @Override
    public Products createProducts(Products products) {
        Products toRet = searchRepository.save(products);
        return toRet;
    }
}
