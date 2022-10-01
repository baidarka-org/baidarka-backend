package com.baidarka.booking.interfaces.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class AdvertisementRequest {
    String name;
    String location;
    Integer seat;
    BigDecimal pricePerPerson;
    String description;
    String supply;
    boolean withDelivery;
    boolean isOneDay;
    boolean isMultiDay;
    SubCategoryRequest subCategory;
}
