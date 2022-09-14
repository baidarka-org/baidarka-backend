package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderRequestToAdvertisementOrderProjectionMapper {
    OrderRequestToAdvertisementOrderProjectionMapper MAPPER =
            Mappers.getMapper(OrderRequestToAdvertisementOrderProjectionMapper.class);

    @Mapping(target = "primaryUser.id", source = "primaryUserId")
    @Mapping(target = "advertisement.id", source = "request.advertisementId")
    @Mapping(target = "orderStatus", expression = "java(OrderStatus.BOOKED)")
    AdvertisementOrderProjection mapFrom(OrderRequest request, Long primaryUserId);
}
