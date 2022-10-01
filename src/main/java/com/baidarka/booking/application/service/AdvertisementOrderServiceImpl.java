package com.baidarka.booking.application.service;

import com.baidarka.booking.application.publisher.NotificationReadyPublisher;
import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.domain.order.repository.AdvertisementOrderRepository;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.infrastructure.exception.DataAccessException;
import com.baidarka.booking.interfaces.facade.AdvertisementOrderInsertionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.baidarka.booking.application.service.PrimaryUserServiceImpl.ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;
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
        try {
            facade.insert(advertisementOrder);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();

        }
    }

    @Override
    public Integer getFreeSeatBy(LocalDateTime date, UUID advertisementId) {
        try {
            return repository.findFreeSeatsBy(date, advertisementId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();

        }
    }

    @Override
    public boolean isAlreadyBookedBy(UUID advertisementId,
                                     Long primaryUserId,
                                     LocalDateTime arrival,
                                     LocalDateTime departure) {
        try {
            return repository.isBookedBy(advertisementId, primaryUserId, arrival, departure);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();

        }
    }

    @Override
    public boolean isAlreadyOrderedAndPassedBy(UUID advertisementId, Long primaryUserId) {
        try {
            return repository.isOrderedAndPassedBy(advertisementId, primaryUserId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();

        }
    }
}
