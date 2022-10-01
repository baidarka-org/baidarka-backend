package com.baidarka.booking.interfaces.dto;

import lombok.Value;

@Value
public class SubCategoryRequest {
    Long id;
    PrimaryCategoryRequest primaryCategory;
}
