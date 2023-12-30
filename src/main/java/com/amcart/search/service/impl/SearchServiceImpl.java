package com.amcart.search.service.impl;

import com.amcart.search.model.AmcartFilter;
import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.entity.Products;
import com.amcart.search.model.request.ProductsSearchRequest;
import com.amcart.search.model.response.ProductsSearchResponse;
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
import java.util.Optional;

@Component
public class SearchServiceImpl implements SearchService {

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public Page<ProductsSearchResponse> searchProducts(String searchTerm, String categoryId, int pageNo, int pageSize,
                                                       List<String> amcartFilter, AmcartSort amcartSort) throws JsonProcessingException {
        Page<ProductsSearchResponse> toRet = null;
        Criteria criteria = null;
        if (Objects.nonNull(searchTerm) && !searchTerm.isBlank()) {
            criteria = new Criteria("brand").fuzzy(searchTerm)
                    .or("name").fuzzy(searchTerm)
                    .or("skuName").fuzzy(searchTerm)
                    .or("skuColor").fuzzy(searchTerm)
                    .or("shortDescription").fuzzy(searchTerm)
                    .or("longDescription").fuzzy(searchTerm)
                    .or("tags").fuzzy(searchTerm);
        }
        if (Objects.nonNull(categoryId) && !categoryId.isBlank()) {
            Criteria andConditions = new Criteria("categoryIds").matches(categoryId);
            if (Objects.nonNull(criteria) && Objects.nonNull(criteria.getField())) {
                criteria = andConditions.and(criteria);
            } else {
                criteria = andConditions;
            }
        }
        if (Objects.nonNull(amcartFilter) && !amcartFilter.isEmpty()) {
            for (Object filter : amcartFilter) {
                AmcartFilter amcartFiltering = new ObjectMapper().convertValue(filter, AmcartFilter.class);
                Criteria andConditions = new Criteria(amcartFiltering.getFieldName());
                if (Objects.nonNull(amcartFiltering.getOperator()) && amcartFiltering.getOperator().equalsIgnoreCase("between")) {
                    andConditions.between(amcartFiltering.getFieldValue()[0], amcartFiltering.getFieldValue()[1]);
                } else {
                    andConditions.matches(amcartFiltering.getFieldValue()[0]);
                }
                if (Objects.nonNull(criteria)) {
                    criteria = andConditions.and(criteria);
                } else {
                    criteria = andConditions;
                }
            }
        }
        Sort sorting = Sort.by(amcartSort.getDirection(), amcartSort.getFieldName());
        Pageable pageable = PageRequest.of(pageNo, pageSize, sorting);
        CriteriaQueryBuilder criteriaQueryBuilder = CriteriaQuery.builder(Objects.nonNull(criteria) ? criteria : new Criteria())
                .withPageable(pageable);
        Query query = new CriteriaQuery(criteriaQueryBuilder);
        SearchHits<Products> results = elasticsearchOperations.search(query, Products.class);
        List<ProductsSearchResponse> searchResultContents = new ArrayList<>();
        if (results != null && !results.isEmpty()) {
            results.getSearchHits().stream().forEach(f -> {
                Products products = f.getContent();
                searchResultContents.add(products.convertToReponseModel());
            });
            toRet = new PageImpl(searchResultContents, pageable, results.getTotalHits());
        }

        //results = elasticsearchTemplate.search(query, Products.class);
        return toRet;
    }

    @Override
    public ProductsSearchResponse createProductSearchData(ProductsSearchRequest productsSearchRequest) {
        Products createdSearchDataResp = searchRepository.save(productsSearchRequest.convertToEntityModel());
        return createdSearchDataResp.convertToReponseModel();
    }

    @Override
    public ProductsSearchResponse updateProductSearchData(String id, ProductsSearchRequest productsSearchRequest) {
        ProductsSearchResponse toRet = new ProductsSearchResponse();
        Optional<Products> existingProductSearchData = searchRepository.findById(id);
        Products updatedProductSearchEntity = null;
        if(Objects.nonNull(existingProductSearchData) && existingProductSearchData.isPresent()){
            Products existingSearchProductEntity = existingProductSearchData.get();
            updatedProductSearchEntity = productsSearchRequest.convertToEntityModel();
            updatedProductSearchEntity.setId(existingSearchProductEntity.getId());
            updatedProductSearchEntity = searchRepository.save(updatedProductSearchEntity);
        }
        else{
           // throw new Exception("ProductSearchData not found with id: " + id);
        }
        toRet = updatedProductSearchEntity.convertToReponseModel();
        return toRet;
    }

    @Override
    public String deleteProductSearchData(String id) {
        Optional<Products> productsOptional = searchRepository.findById(id);
        if(productsOptional.isPresent()){
            searchRepository.deleteById(id);
        }
        return "Product Search data deleted successfully";
    }
}
