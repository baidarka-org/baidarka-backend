package com.baidarka.booking.application.service;

import com.baidarka.booking.application.publisher.NotificationReadyPublisher;
import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.advertisement.repository.AdvertisementRepository;
import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.infrastructure.exception.DataAccessException;
import com.baidarka.booking.interfaces.facade.AdvertisementInsertionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.baidarka.booking.application.service.PrimaryUserServiceImpl.ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static com.baidarka.booking.infrastructure.model.NotificationType.ADVERTISEMENT_CREATED;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository repository;
    private final AdvertisementInsertionFacade facade;
    private final NotificationReadyPublisher publisher;

    @Override
    public UUID save(AdvertisementProjection advertisement) {
        if (repository.isExistsBy(advertisement.getName())) {
            throw factory()
                    .code(DATA_IS_NOT_VALID)
                    .message("Advertisement with such name already exists")
                    .get();
        }

        publisher.publish(
                ADVERTISEMENT_CREATED,
                advertisement
                        .getAdvertisementOwner().getPrimaryUser(),
                advertisement.getName(),
                advertisement
                        .getSubCategory().getName());

        return facade.insert(advertisement);
    }

    @Override
    public List<AdvertisementProjection> getAdvertisementsBy(Long subCategoryId) {
        try {
            final var advertisements =
                    repository.findAdvertisementsBy(subCategoryId);

            return advertisements.stream()
                    .map(AdvertisementProjection::copy)
                    .toList();
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_IS_NOT_VALID)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public AdvertisementProjection getAdvertisementBy(Long subCategoryId,
                                                      UUID advertisementId) {
        try {
            final var advertisement =
                    repository.findAdvertisementBy(subCategoryId, advertisementId);

            return advertisement.copy();
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public boolean isExistsBy(UUID advertisementId) {
        try {
            return repository.isExistsBy(advertisementId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public String getAdvertisementNameBy(UUID advertisementId) {
        try {
            return repository.findAdvertisementNameBy(advertisementId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public boolean isOwnerBy(Long primaryUserId, UUID advertisementId) {
        try {
            return repository.isOwnerBy(primaryUserId, advertisementId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }
}
