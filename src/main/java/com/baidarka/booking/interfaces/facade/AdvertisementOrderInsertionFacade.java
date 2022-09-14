package com.baidarka.booking.interfaces.facade;

import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.domain.order.repository.AdvertisementOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertisementOrderInsertionFacade {
    private final AdvertisementOrderRepository advertisementOrderRepository;

    public void insert(AdvertisementOrderProjection advertisementOrder) {
        advertisementOrderRepository.insert(
                advertisementOrder.getOrderStatus(), advertisementOrder.getSeat(),
                advertisementOrder.getArrival(), advertisementOrder.getDeparture(),
                advertisementOrder.getPrimaryUser().getId(),
                advertisementOrder.getAdvertisement().getId());
    }
}
