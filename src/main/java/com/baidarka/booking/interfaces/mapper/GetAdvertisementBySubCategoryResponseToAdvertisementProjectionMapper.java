package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.GetAdvertisementBySubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GetAdvertisementBySubCategoryResponseToAdvertisementProjectionMapper {
    GetAdvertisementBySubCategoryResponseToAdvertisementProjectionMapper MAPPER =
            Mappers.getMapper(GetAdvertisementBySubCategoryResponseToAdvertisementProjectionMapper.class);

    GetAdvertisementBySubCategoryResponse mapFrom(AdvertisementProjection advertisement);
}
