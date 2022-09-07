package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.signup.repository.PrimaryUserRepository;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;

@Service
@RequiredArgsConstructor
public class PrimaryUserServiceImpl implements PrimaryUserService {
    private final PrimaryUserRepository repository;

    @Override
    public void insert(String keycloakUserId) {
        try {
            if (repository.isExists(keycloakUserId)) {
                throw ExceptionFactory.factory()
                        .code(DATA_IS_NOT_VALID)
                        .message("User already exists")
                        .get();
            }

            repository.insert(keycloakUserId);
        } catch (DataAccessException dae) {
            throw ExceptionFactory.factory()
                    .code(DATA_ACCESS_DENIED)
                    .get();
        }
    }
}
