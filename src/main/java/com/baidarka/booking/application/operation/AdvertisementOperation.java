package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.interfaces.dto.CreateAdvertisementRequest;
import com.baidarka.booking.interfaces.dto.CreateAdvertisementResponse;
import com.baidarka.booking.interfaces.dto.GetAdvertisementBySubCategoryResponse;
import com.baidarka.booking.interfaces.dto.GetSingleAdvertisementBySubCategoryResponse;
import com.baidarka.booking.interfaces.mapper.AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper;
import com.baidarka.booking.interfaces.mapper.CreateAdvertisementRequestToAdvertisementProjectionMapper;
import com.baidarka.booking.interfaces.mapper.GetAdvertisementBySubCategoryResponseToAdvertisementProjectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;


@Component
@RequiredArgsConstructor
public class AdvertisementOperation {
    private final AdvertisementService advertisementService;
    private final PrimaryUserService primaryUserService;
    private final AdvertisementOrderService advertisementOrderService;

    public CreateAdvertisementResponse create(CreateAdvertisementRequest request, String keycloakUserId) {
        final var primaryUser =
                PrimaryUserProjection.builder()
                        .id(primaryUserService.getPrimaryUserIdBy(keycloakUserId))
                        .keycloakUserId(UUID.fromString(keycloakUserId)) //todo
                        .build();

        final var advertisement =
                CreateAdvertisementRequestToAdvertisementProjectionMapper.MAPPER.mapFrom(request, AdvertisementOwner.get(primaryUser));

        return CreateAdvertisementResponse.builder()
                .advertisementId(advertisementService.save(advertisement).toString())
                .build();
    }

    public List<GetAdvertisementBySubCategoryResponse> getBy(Long subCategoryId) {
        final var advertisements = advertisementService.getAdvertisementsBy(subCategoryId);

        return advertisements.stream()
                .map(GetAdvertisementBySubCategoryResponseToAdvertisementProjectionMapper.MAPPER::mapFrom)
                .toList();
    }

    public GetSingleAdvertisementBySubCategoryResponse getBy(Long subCategoryId, UUID advertisementId) {
        final var advertisement =
                advertisementService.getAdvertisementBy(subCategoryId, advertisementId);

        final var freeSeats =
                advertisementOrderService.getFreeSeatBy(now(), advertisementId);

        return AdvertisementProjectionToGetSingleAdvertisementBySubCategoryResponseMapper.MAPPER.map(
                advertisement.copy(), freeSeats);
    }
}
