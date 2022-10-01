package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.infrastructure.model.ErrorCode;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import com.baidarka.booking.interfaces.mapper.OrderRequestToAdvertisementOrderProjectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderOperation {
    private final AdvertisementOrderService advertisementOrderService;
    private final PrimaryUserService primaryUserService;
    private final AdvertisementService advertisementService;

    public void order(OrderRequest request, String keycloakUserId) {
        if (!advertisementService.isExistsBy(request.getAdvertisementId())) {
            throw ExceptionFactory.factory()
                    .code(ErrorCode.DATA_IS_NOT_FOUND)
                    .message("Advertisement was not found")
                    .get();
        }

        final var primaryUserId = primaryUserService.getPrimaryUserIdBy(keycloakUserId);

        final var advertisementOrder =
                OrderRequestToAdvertisementOrderProjectionMapper.MAPPER.mapFrom(request, primaryUserId);

        advertisementOrderService.save(advertisementOrder);
    }
}
