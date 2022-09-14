package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.CreateAdvertisementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreateAdvertisementRequestToAdvertisementProjectionMapper {
    CreateAdvertisementRequestToAdvertisementProjectionMapper MAPPER =
            Mappers.getMapper(CreateAdvertisementRequestToAdvertisementProjectionMapper.class);

    AdvertisementProjection mapFrom(CreateAdvertisementRequest request, AdvertisementOwner advertisementOwner);
}
