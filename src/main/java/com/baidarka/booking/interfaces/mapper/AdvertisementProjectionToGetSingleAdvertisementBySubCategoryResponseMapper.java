package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.AdvertisementBySubCategoryResponse;
import com.baidarka.booking.interfaces.dto.FreeSeatsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper {
    AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper MAPPER =
            Mappers.getMapper(AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper.class);

    @Mapping(
            target = "advertisementOwner.firstName",
            expression = "java(advertisementOwner.getFirstName())")
    @Mapping(
            target = "advertisementOwner.primaryUser.id",
            expression = "java(primaryUserProjection.getId())")
    AdvertisementBySubCategoryResponse mapFrom(AdvertisementProjection advertisementProjection);
}
