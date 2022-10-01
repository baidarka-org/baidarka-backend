package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface OrderRequestToAdvertisementOrderMapper {
    OrderRequestToAdvertisementOrderMapper MAPPER =
            Mappers.getMapper(OrderRequestToAdvertisementOrderMapper.class);

    @Mapping(target = "primaryUser.id", source = "primaryUserId")
    @Mapping(target = "orderStatus", expression = "java(OrderStatus.BOOKED)")
    AdvertisementOrderProjection mapFrom(OrderRequest request, Long primaryUserId);

    @Mapping(target = "name", source = "advertisementName")
    @Mapping(target = "id", source = "advertisementId")
    AdvertisementProjection mapFrom(UUID advertisementId, String advertisementName);

    @Mapping(target = "advertisement.id", source = "advertisement.id")
    @Mapping(target = "id", source = "advertisementOrder.id")
    @Mapping(target = "advertisement.name", source = "advertisement.name")
    @Mapping(target = "seat", source = "advertisementOrder.seat")
    AdvertisementOrderProjection mapFrom(AdvertisementOrderProjection advertisementOrder, AdvertisementProjection advertisement);

    default AdvertisementOrderProjection map(OrderRequest request,
                                             Long primaryUserId,
                                             String advertisementName) {
        final var advertisement =
                mapFrom(request.getAdvertisementId(), advertisementName);

        final var order =
                mapFrom(request, primaryUserId);

        return mapFrom(order, advertisement);
    }
}
