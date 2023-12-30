package com.amcart.search.service.impl;

import com.amcart.search.model.AmcartFilter;
import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.entity.Products;
import com.amcart.search.repository.SearchRepository;
import com.amcart.search.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
import java.util.Objects;

@Component
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Iterable<Products> loadAllProducts() {
        Iterable<Products> toRet = searchRepository.findAll();
        return toRet;
    }

    @Override
    public Page<Products> searchProducts(String searchTerm, String categoryId, int pageNo, int pageSize,
                                         List<String> amcartFilter, AmcartSort amcartSort) throws JsonProcessingException {
        Page<Products> toRet = null;
        Criteria criteria = null;
        if(Objects.nonNull(searchTerm) && !searchTerm.isBlank()){
//            criteria = new Criteria("brand").fuzzy(searchTerm)
//                    .or("name").fuzzy(searchTerm)
//                    .or("skuName").fuzzy(searchTerm)
//                    .or("skuColor").fuzzy(searchTerm)
//                    .or("shortDescription").fuzzy(searchTerm)
//                    .or("longDescription").fuzzy(searchTerm)
//                    .or("tags").fuzzy(searchTerm);
            criteria = new Criteria("brand").fuzzy(searchTerm)
                    .or("name").fuzzy(searchTerm)
                    .or("skuName").fuzzy(searchTerm)
                    .or("skuColor").fuzzy(searchTerm)
                    .or("shortDescription").fuzzy(searchTerm)
                    .or("longDescription").fuzzy(searchTerm)
                    .or("tags").fuzzy(searchTerm);
        }
        if(Objects.nonNull(categoryId) && !categoryId.isBlank()){
            Criteria andConditions = new Criteria("categoryIds").matches(categoryId);
            if(Objects.nonNull(criteria) && Objects.nonNull(criteria.getField())){
                criteria = andConditions.and(criteria);
            }
            else{
                criteria = andConditions;
            }
        }
        if(Objects.nonNull(amcartFilter) && !amcartFilter.isEmpty()){
            for(Object filter: amcartFilter){
                AmcartFilter amcartFiltering = new ObjectMapper().convertValue(filter, AmcartFilter.class);
                Criteria andConditions = new Criteria(amcartFiltering.getFieldName());
                if(Objects.nonNull(amcartFiltering.getOperator()) && amcartFiltering.getOperator().equalsIgnoreCase("between")){
                    andConditions.between(amcartFiltering.getFieldValue()[0], amcartFiltering.getFieldValue()[1]);
                }
                else {
                    andConditions.matches(amcartFiltering.getFieldValue()[0]);
                }
                if(Objects.nonNull(criteria)){
                    criteria = andConditions.and(criteria);
                }
                else{
                    criteria = andConditions;
                }
            }
        }
        Sort sorting = Sort.by(amcartSort.getDirection(), amcartSort.getFieldName());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sorting);
        CriteriaQueryBuilder criteriaQueryBuilder = CriteriaQuery.builder(criteria)
                .withPageable(pageable);
        Query query = new CriteriaQuery(criteriaQueryBuilder);
        SearchHits<Products> results = elasticsearchOperations.search(query, Products.class);
        List<Products> searchResultContents = new ArrayList<>();
        if(results != null && !results.isEmpty()){
            results.getSearchHits().stream().forEach(f->{
                Products products = f.getContent();
                searchResultContents.add(products);
            });
            toRet = new PageImpl(searchResultContents, pageable, results.getTotalHits());
        }

        results = elasticsearchTemplate.search(query, Products.class);
        return toRet;
    }

    @Override
    public Products createProducts(Products products) {
        Products toRet = searchRepository.save(products);
        return toRet;
    }
}
