package com.baidarka.booking.domain.category.projection;

import com.baidarka.booking.domain.category.projection.PrimaryCategoryProjection;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubCategoryProjection {
    Long id;
    String name;
    //Map<String, String> attributes = new HashMap<>();
    PrimaryCategoryProjection primaryCategory;
}
