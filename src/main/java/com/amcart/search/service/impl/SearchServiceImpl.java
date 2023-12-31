package com.amcart.search.service.impl;

import com.amcart.search.model.AmcartFilter;
import com.amcart.search.model.AmcartSort;
import com.amcart.search.model.entity.Products;
import com.amcart.search.model.request.ProductsSearchRequest;
import com.amcart.search.model.response.ProductsSearchResponse;
import com.amcart.search.model.response.ProductsSuggestionResponse;
import com.amcart.search.repository.SearchRepository;
import com.amcart.search.service.SearchService;
import com.amcart.search.util.CommonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
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
//    //@Autowired
//    ElasticsearchTemplate elasticsearchTemplate;


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
    public Page<ProductsSuggestionResponse> suggestProducts(String searchTerm, String categoryId, int pageNo, int pageSize) {
        Page<ProductsSuggestionResponse> toRet = null;

        Sort sorting = Sort.by(Sort.Direction.DESC, "_score");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sorting);
        searchTerm = CommonUtil.replaceUnwantedCharacter(searchTerm);
        searchTerm = CommonUtil.escapeMetaCharacters(searchTerm) + "*";

        //String query = "{\"query_string\":{\"fields\":[\"name\"],\"query\":\""+searchTerm+"\",\"minimum_should_match\":0},\"match\":{\"name\":{\"query\":\""+searchTerm+"\",\"fuzziness\": \"AUTO\",\"minimum_should_match\":0}}}";
        //String query = "{\"query_string\":{\"fields\":[\"name\"],\"query\":\""+searchTerm+"\",\"minimum_should_match\":0}," +
        //  "\"match\":{\"categoryIds\":{\"query\":\""+categoryId+"\",\"fuzziness\": \"AUTO\",\"minimum_should_match\":0}}"+
        //"}";
        //String query = "{\"match\":{\"name\":{\"query\":\""+searchTerm+"\",\"fuzziness\": \"AUTO\",\"minimum_should_match\":0}}}";
        //String query = "{\"regexp\":{\"name\":{\"value\":\""+searchTerm+"\",\"flags\": \"ALL\",\"minimum_should_match\":0}}}";
        //String query = "{\"bool\":{\"should\":[{\"query_string\":{\"fields\":[\"name\"],\"query\":\"searchTerm\",\"minimum_should_match\":0}},{\"match\":{\"categoryIds\":{\"query\":\"Electronics\",\"fuzziness\":\"fuzziness\"}}}]}}";
        //String query = "{\"bool\":{\"must\":{\"term\":{\"name\":\"kimchy\"}},\"filter\":{\"term\":{\"tags\":\"production\"}},\"should\":[{\"term\":{\"tags\":\"env1\"}},{\"term\":{\"tags\":\"deployed\"}}],\"minimum_should_match\":1,\"boost\":1.0}}";
        String query = "{\"bool\":{\"should\":[{\"query_string\":{\"fields\":[\"name\"],\"query\":\"" + searchTerm + "\"}}" +
                ",{\"match\":{\"name\":{\"query\":\"" + searchTerm + "\",\"fuzziness\": \"AUTO\",\"minimum_should_match\":0}}}]" +
                ",\"minimum_should_match\":1,\"boost\":1.0}}";
        if (Objects.nonNull(categoryId) && !categoryId.isBlank()) {
            query = "{\"bool\":{\"must\":{\"match\":{\"categoryIds\":\"" + categoryId + "\"}}," +
                    "\"should\":[{\"query_string\":{\"fields\":[\"name\"],\"query\":\"" + searchTerm + "\"}}," +
                    "{\"match\":{\"name\":{\"query\":\"" + searchTerm + "\",\"fuzziness\": \"AUTO\",\"minimum_should_match\":0}}}]," +
                    "\"minimum_should_match\":1,\"boost\":1.0}}";

        }
        Query searchQuery = new StringQuery(query).setPageable(pageable);


        SearchHits<Products> results = elasticsearchOperations.search(searchQuery, Products.class);
        List<ProductsSuggestionResponse> searchResultContents = new ArrayList<>();
        if (results != null && !results.isEmpty()) {
            results.getSearchHits().stream().forEach(f -> {
                Products products = f.getContent();
                searchResultContents.add(products.convertToSuggestionReponseModel());
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
        if (Objects.nonNull(existingProductSearchData) && existingProductSearchData.isPresent()) {
            Products existingSearchProductEntity = existingProductSearchData.get();
            updatedProductSearchEntity = productsSearchRequest.convertToEntityModel();
            updatedProductSearchEntity.setId(existingSearchProductEntity.getId());
            updatedProductSearchEntity = searchRepository.save(updatedProductSearchEntity);
        } else {
            // throw new Exception("ProductSearchData not found with id: " + id);
        }
        toRet = updatedProductSearchEntity.convertToReponseModel();
        return toRet;
    }

    @Override
    public String deleteProductSearchData(String id) {
        Optional<Products> productsOptional = searchRepository.findById(id);
        if (productsOptional.isPresent()) {
            searchRepository.deleteById(id);
        }
        return "Product Search data deleted successfully";
    }
}
