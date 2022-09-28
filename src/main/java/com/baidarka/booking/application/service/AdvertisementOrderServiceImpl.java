package com.baidarka.booking.application.service;

import com.baidarka.booking.application.publisher.NotificationReadyPublisher;
import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.domain.order.repository.AdvertisementOrderRepository;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.interfaces.facade.AdvertisementOrderInsertionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.model.NotificationType.ADVERTISEMENT_BOOKED;

@Service
@RequiredArgsConstructor
public class AdvertisementOrderServiceImpl implements AdvertisementOrderService {
    private final AdvertisementOrderInsertionFacade facade;
    private final AdvertisementOrderRepository repository;
    private final NotificationReadyPublisher publisher;

    @Override
    public void save(AdvertisementOrderProjection advertisementOrder) {
        publisher.publish(
                ADVERTISEMENT_BOOKED,
                advertisementOrder.getPrimaryUser(),
                advertisementOrder.getAdvertisement().getName());

        facade.insert(advertisementOrder);
    }

    @Override
    public Integer getFreeSeatBy(LocalDateTime date, UUID advertisementId) {
        return repository.findFreeSeatBy(date, advertisementId);
    }

    @Override
    public boolean isAlreadyBookedBy(UUID advertisementId, Long primaryUserId, LocalDateTime arrival, LocalDateTime departure) {
        return repository.isBookedBy(advertisementId, primaryUserId, arrival, departure);
    }

    @Override
    public boolean isAlreadyOrderedAndPassedBy(UUID advertisementId, Long primaryUserId) {
        return repository.isOrderedAndPassedBy(advertisementId, primaryUserId);
    }
}
