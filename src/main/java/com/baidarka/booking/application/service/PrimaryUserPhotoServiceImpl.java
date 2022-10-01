package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.photo.projection.PhotoProjection;
import com.baidarka.booking.domain.photo.primaryuser.repository.PrimaryUserPhotoRepository;
import com.baidarka.booking.domain.photo.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.baidarka.booking.application.service.PrimaryUserServiceImpl.ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;

@Service
@RequiredArgsConstructor
public class PrimaryUserPhotoServiceImpl implements PhotoService {
    private final PrimaryUserPhotoRepository repository;

    @Override
    public void save(PhotoProjection projection, String keycloakUserId) {
        try {
            repository.update(
                    projection.getId(),
                    projection.getKey(), keycloakUserId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public void deletePhotoBy(String key) {
        try {
            repository.deleteBy(key);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }
}
