package com.baidarka.booking.domain.order.service;

import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AdvertisementOrderService {
    void save(AdvertisementOrderProjection advertisementOrder);
    Integer getFreeSeatBy(LocalDateTime date, UUID advertisementId);
    boolean isAlreadyBookedBy(UUID advertisementId, Long primaryUserId, LocalDateTime arrival, LocalDateTime departure);
    boolean isAlreadyOrderedAndPassedBy(UUID advertisementId, Long primaryUserId);
}
