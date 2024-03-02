package com.amcart.search.repository;

import com.amcart.search.model.entity.Products;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends ElasticsearchRepository<Products, String> {
}
