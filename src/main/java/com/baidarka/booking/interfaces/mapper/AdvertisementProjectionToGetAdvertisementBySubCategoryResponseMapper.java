package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.AdvertisementsBySubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementProjectionToGetAdvertisementBySubCategoryResponseMapper {
    AdvertisementProjectionToGetAdvertisementBySubCategoryResponseMapper MAPPER =
            Mappers.getMapper(AdvertisementProjectionToGetAdvertisementBySubCategoryResponseMapper.class);

    AdvertisementsBySubCategoryResponse mapFrom(AdvertisementProjection projection);
}
