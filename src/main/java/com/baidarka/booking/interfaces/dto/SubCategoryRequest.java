package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

@Value
public class SubCategoryRequest {
    Long id;
    //String name; todo
    PrimaryCategoryRequest primaryCategory;
}
