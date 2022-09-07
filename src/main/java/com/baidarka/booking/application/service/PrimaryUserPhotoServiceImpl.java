package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.photo.projection.PrimaryUserPhotoProjection;
import com.baidarka.booking.domain.photo.repository.PrimaryUserPhotoRepository;
import com.baidarka.booking.domain.photo.service.PrimaryUserPhotoService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;


@Service
@RequiredArgsConstructor
public class PrimaryUserPhotoServiceImpl implements PrimaryUserPhotoService {
    private final PrimaryUserPhotoRepository repository;

    @Override
    public void save(PrimaryUserPhotoProjection projection, String keycloakUserId) {
        try {
            repository.update(
                    projection.getId(),
                    projection.getKey(), keycloakUserId);
        } catch (DataAccessException dae) {
            throw ExceptionFactory.factory()
                    .code(DATA_ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public void deleteBy(String key) {
        try {
            repository.delete(key);
        } catch (DataAccessException dae) {
            throw ExceptionFactory.factory()
                    .code(DATA_ACCESS_DENIED)
                    .get();
        }
    }
}
