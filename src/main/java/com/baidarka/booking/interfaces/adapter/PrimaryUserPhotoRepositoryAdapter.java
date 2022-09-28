package com.baidarka.booking.interfaces.adapter;

import com.baidarka.booking.domain.photo.primaryuser.repository.PrimaryUserPhotoRepository;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;

@Component
@RequiredArgsConstructor
public class PrimaryUserPhotoRepositoryAdapter {
    private final PrimaryUserPhotoRepository repository;

    public String findKeyBy(String keycloakUserId) {
        try {
            return repository.findKeyBy(keycloakUserId);
        } catch (DataAccessException dae) {
            throw ExceptionFactory.factory()
                    .code(DATA_ACCESS_DENIED)
                    .get();

        }
    }
}
