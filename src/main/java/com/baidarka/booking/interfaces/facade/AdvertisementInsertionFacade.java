package com.baidarka.booking.interfaces.facade;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.advertisement.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AdvertisementInsertionFacade {
    private final AdvertisementRepository advertisementRepository;

    public UUID insert(AdvertisementProjection advertisement) {
        return advertisementRepository.insert(
                advertisement.getName(), advertisement.getLocation(),
                advertisement.getSeat(), advertisement.getPricePerPerson(),
                advertisement.getDescription(), advertisement.getSupply(),
                advertisement.isWithDelivery(), advertisement.isOneDay(),
                advertisement.isMultiDay(), advertisement.getSubCategory().getId(),
                advertisement.getAdvertisementOwner().getPrimaryUser().getId());
    }
}
