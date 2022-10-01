package com.baidarka.booking.domain.advertisement.service;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;

import java.util.List;
import java.util.UUID;

public interface AdvertisementService {
    UUID save(AdvertisementProjection projection);
    List<AdvertisementProjection> getAdvertisementsBy(Long subCategoryId);
    AdvertisementProjection getAdvertisementBy(Long subCategoryId,
                                               UUID advertisementId);

    boolean isExistsBy(UUID advertisementId);
}
