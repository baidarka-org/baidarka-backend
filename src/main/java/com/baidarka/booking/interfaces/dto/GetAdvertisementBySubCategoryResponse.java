package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class GetAdvertisementBySubCategoryResponse {
    UUID id;
    String name;
    String location;
    Integer seat;
    BigDecimal pricePerPerson;
    String description;
    String supply;
    boolean withDelivery;
    boolean isOneDay;
    boolean isMultiDay;
    AdvertisementOwnerResponse advertisementOwner;
}
