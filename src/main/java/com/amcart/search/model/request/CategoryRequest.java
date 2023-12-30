package com.amcart.search.model.request;

import com.amcart.search.model.CategoryStatus;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    String categoryId;
    String name;
    String displayName;
    String parentCategoryId;
    CategoryStatus status;
    Map<String, Object> properties;
}
