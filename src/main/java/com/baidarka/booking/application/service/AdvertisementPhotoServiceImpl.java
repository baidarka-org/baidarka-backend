package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.photo.projection.PhotoProjection;
import com.baidarka.booking.domain.photo.advertisement.repository.AdvertisementPhotoRepository;
import com.baidarka.booking.domain.photo.service.PhotoService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.google.common.base.Throwables;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;

import static com.amazonaws.util.Throwables.getRootCause;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;

@Service
@RequiredArgsConstructor
public class AdvertisementPhotoServiceImpl implements PhotoService {
    private final AdvertisementPhotoRepository repository;

    @Override
    public void save(PhotoProjection projection, String advertisementId) {
        try {
            repository.update(projection.getId(), projection.getKey(), advertisementId);
        } catch (UncategorizedSQLException usqle) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message(getRootCause(usqle).getMessage())
                    .get();
        }
    }

    @Override
    public void deleteBy(String key) {
        repository.delete(key);
    }
}
