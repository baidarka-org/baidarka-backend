package com.baidarka.booking.domain.advertisement.projection;

import com.baidarka.booking.domain.category.projection.SubCategoryProjection;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class AdvertisementProjection {
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
    SubCategoryProjection subCategory;
    AdvertisementOwner advertisementOwner;

    public AdvertisementProjection copy() {
        return AdvertisementProjection.builder()
                .id(id)
                .name(name)
                .location(location)
                .seat(seat)
                .pricePerPerson(pricePerPerson)
                .description(description)
                .supply(supply)
                .withDelivery(withDelivery)
                .isOneDay(isOneDay)
                .isMultiDay(isMultiDay)
                .subCategory(subCategory)
                .advertisementOwner(
                        AdvertisementOwner
                                .get(advertisementOwner
                                        .getPrimaryUser()))
                .build();
    }
}
