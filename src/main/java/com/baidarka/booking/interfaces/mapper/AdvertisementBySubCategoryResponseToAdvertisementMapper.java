package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.AdvertisementsBySubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementBySubCategoryResponseToAdvertisementMapper {
    AdvertisementBySubCategoryResponseToAdvertisementMapper MAPPER =
            Mappers.getMapper(AdvertisementBySubCategoryResponseToAdvertisementMapper.class);

    AdvertisementsBySubCategoryResponse mapFrom(AdvertisementProjection advertisement);
}
