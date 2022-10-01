package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.interfaces.dto.AdvertisementBySubCategoryResponse;
import com.baidarka.booking.interfaces.dto.AdvertisementRequest;
import com.baidarka.booking.interfaces.dto.AdvertisementResponse;
import com.baidarka.booking.interfaces.dto.AdvertisementsBySubCategoryResponse;
import com.baidarka.booking.interfaces.mapper.AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper;
import com.baidarka.booking.interfaces.mapper.AdvertisementRequestToAdvertisementMapper;
import com.baidarka.booking.interfaces.mapper.AdvertisementBySubCategoryResponseToAdvertisementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementOperation {
    private final AdvertisementService advertisementService;
    private final PrimaryUserService primaryUserService;

    public AdvertisementResponse create(AdvertisementRequest request, String keycloakUserId) {
        final var primaryUser =
                PrimaryUserProjection.builder()
                        .id(primaryUserService.getPrimaryUserIdBy(keycloakUserId))
                        .keycloakUserId(UUID.fromString(keycloakUserId))
                        .build();

        final var advertisement =
                AdvertisementRequestToAdvertisementMapper.MAPPER
                        .mapFrom(request, AdvertisementOwner.get(primaryUser));

        return AdvertisementResponse.builder()
                .advertisementId(advertisementService.save(advertisement).toString())
                .build();
    }

    public List<AdvertisementsBySubCategoryResponse> getBy(Long subCategoryId) {
        final var advertisements = advertisementService.getAdvertisementsBy(subCategoryId);

        return advertisements.stream()
                .map(AdvertisementBySubCategoryResponseToAdvertisementMapper.MAPPER::mapFrom)
                .toList();
    }

    public AdvertisementBySubCategoryResponse getBy(Long subCategoryId, UUID advertisementId) {
        final var advertisement =
                advertisementService.getAdvertisementBy(subCategoryId, advertisementId);

        return AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper.MAPPER.mapFrom(
                advertisement.copy());
    }
}
