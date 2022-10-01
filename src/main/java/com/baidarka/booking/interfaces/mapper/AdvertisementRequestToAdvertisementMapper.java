package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.AdvertisementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdvertisementRequestToAdvertisementMapper {
    AdvertisementRequestToAdvertisementMapper MAPPER =
            Mappers.getMapper(AdvertisementRequestToAdvertisementMapper.class);

    AdvertisementProjection mapFrom(AdvertisementRequest request, AdvertisementOwner advertisementOwner);
}
