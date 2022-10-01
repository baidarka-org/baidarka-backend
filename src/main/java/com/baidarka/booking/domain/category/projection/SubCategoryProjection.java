package com.baidarka.booking.domain.category.projection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubCategoryProjection {
    Long id;
    String name;
    PrimaryCategoryProjection primaryCategory;
}
