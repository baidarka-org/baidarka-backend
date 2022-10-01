package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.photo.projection.PhotoProjection;
import com.baidarka.booking.domain.photo.repository.AdvertisementPhotoRepository;
import com.baidarka.booking.domain.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementPhotoServiceImpl implements PhotoService {
    private final AdvertisementPhotoRepository repository;

    @Override
    public void save(PhotoProjection projection, String advertisementId) {
        repository.update(projection.getId(), projection.getKey(), advertisementId);
    }

    @Override
    public void deleteBy(String key) {
        repository.delete(key);
    }
}
