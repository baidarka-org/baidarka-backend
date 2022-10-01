package com.baidarka.booking.interfaces.facade;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.advertisement.repository.AdvertisementRepository;
import com.baidarka.booking.infrastructure.model.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;

@Component
@RequiredArgsConstructor
public class AdvertisementInsertionFacade {
    private final AdvertisementRepository advertisementRepository;

    public UUID insert(AdvertisementProjection advertisement) {
        try {
            return advertisementRepository.insert(
                    advertisement.getName(), advertisement.getLocation(),
                    advertisement.getSeat(), advertisement.getPricePerPerson(),
                    advertisement.getDescription(), advertisement.getSupply(),
                    advertisement.isWithDelivery(), advertisement.isOneDay(),
                    advertisement.isMultiDay(), advertisement.getSubCategory().getId(),
                    advertisement.getAdvertisementOwner().getPrimaryUser().getId());
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(dae.getMessage())
                    .get();
        }
    }
}
