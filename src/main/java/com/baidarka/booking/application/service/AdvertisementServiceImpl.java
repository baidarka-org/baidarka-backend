package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.advertisement.repository.AdvertisementRepository;
import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.interfaces.facade.AdvertisementInsertionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository repository;
    private final AdvertisementInsertionFacade facade;

    @Override
    public UUID save(AdvertisementProjection advertisement) {
        if (repository.isExists(advertisement.getName())) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message("Advertisement with such name already exists")
                    .get();
        }
        return facade.insert(advertisement);
    }

    @Override
    public List<AdvertisementProjection> getAdvertisementsBy(Long subCategoryId) {
        final var advertisements =
                repository.findAdvertisementsBy(subCategoryId);

        return advertisements.stream()
                .map(AdvertisementProjection::copy)
                .toList();
    }

    @Override
    public AdvertisementProjection getAdvertisementBy(Long subCategoryId,
                                                      UUID advertisementId) {
        final var advertisement =
                repository.findAdvertisementBy(subCategoryId, advertisementId);

        return advertisement.copy();
    }

    @Override
    public boolean isExistsBy(UUID advertisementId) {
        return repository.isExists(advertisementId);
    }
}
