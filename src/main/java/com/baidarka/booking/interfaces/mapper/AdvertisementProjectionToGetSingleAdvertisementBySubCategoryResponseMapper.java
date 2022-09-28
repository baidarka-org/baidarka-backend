package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.interfaces.dto.FreeSeatsByDateResponse;
import com.baidarka.booking.interfaces.dto.GetSingleAdvertisementBySubCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static java.time.LocalDateTime.now;

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
    GetSingleAdvertisementBySubCategoryResponse mapFrom(AdvertisementProjection advertisementProjection);

    @Mapping(target = "freeSeatsByDate", source = "freeSeatsByDate")
    GetSingleAdvertisementBySubCategoryResponse mapFrom(GetSingleAdvertisementBySubCategoryResponse getSingleAdvertisementBySubCategory, FreeSeatsByDateResponse freeSeatsByDate);

    default GetSingleAdvertisementBySubCategoryResponse map(AdvertisementProjection advertisementProjection, Integer seat) {
        final var mappedAdvertisement =
                mapFrom(advertisementProjection);

        final var freeSeatsByDate =
                FreeSeatsByDateResponse.builder()
                        .seat(seat)
                        .build();

        return mapFrom(mappedAdvertisement, freeSeatsByDate);
    }
}
