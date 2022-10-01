package com.baidarka.booking.interfaces.adapter;

import com.baidarka.booking.domain.photo.advertisement.repository.AdvertisementPhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;

@Component
@RequiredArgsConstructor
public class AdvertisementPhotoRepositoryAdapter {
    private final AdvertisementPhotoRepository repository;

    public List<String> findKeysBy(String advertisementId) {
        try {
            return repository.findKeysBy(advertisementId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(dae.getMessage())
                    .get();

        }
    }

    public String findKeyBy(String photoId) {
        try {
            return repository.findKeyBy(photoId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(dae.getMessage())
                    .get();

        }
    }
}
