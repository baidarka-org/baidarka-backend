package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.interfaces.dto.FreeSeatsRequest;
import com.baidarka.booking.interfaces.dto.FreeSeatsResponse;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import com.baidarka.booking.interfaces.mapper.OrderRequestToAdvertisementOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_FOUND;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static com.baidarka.booking.interfaces.dto.FreeSeatsResponse.builder;

@Component
@RequiredArgsConstructor
public class OrderOperation {
    private final AdvertisementOrderService advertisementOrderService;
    private final PrimaryUserService primaryUserService;
    private final AdvertisementService advertisementService;

    public void order(OrderRequest request, String keycloakUserId) {
        if (!advertisementService.isExistsBy(request.getAdvertisementId())) {
            throw factory()
                    .code(DATA_IS_NOT_FOUND)
                    .message("Advertisement was not found")
                    .get();
        }

        final var primaryUserId =
                primaryUserService.getPrimaryUserIdBy(keycloakUserId);

        if (advertisementOrderService.isAlreadyBookedBy(
                request.getAdvertisementId(),
                primaryUserId,
                request.getArrival(), request.getDeparture())) {
            throw factory()
                    .code(DATA_IS_NOT_VALID)
                    .message("You have already booked this advertisement on such dates")
                    .get();
        }

        final var advertisementName =
                advertisementService.getAdvertisementNameBy(request.getAdvertisementId());

        final var advertisementOrder =
                OrderRequestToAdvertisementOrderMapper.MAPPER
                        .map(request, primaryUserId, advertisementName);

        advertisementOrderService.save(advertisementOrder);
    }

    public FreeSeatsResponse getFreeSeats(FreeSeatsRequest request) {
        final var freeSeats = advertisementOrderService
                .getFreeSeatBy(
                        request.getDate(),
                        request.getAdvertisementId());

        return builder()
                .seat(freeSeats)
                .build();
    }
}
