package com.amcart.search.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    String id;
    String categoryId;
    String name;
    String displayName;
    CategoryResponse subCategories;
    CategoryStatus status;
    boolean isDeleted;
    Map<String, Object> properties;
    long createdOn;
    long updatedOn;
    String createdBy;
    String updatedBy;
}
