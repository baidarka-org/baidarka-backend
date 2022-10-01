package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.signup.repository.PrimaryUserRepository;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;

@Service
@RequiredArgsConstructor
public class PrimaryUserServiceImpl implements PrimaryUserService {
    public static final String ACCESS_DENIED = "Access denied by database";

    private final PrimaryUserRepository repository;

    @Override
    public void save(String keycloakUserId) {
        try {
            if (repository.isExistsBy(keycloakUserId)) {
                throw factory()
                        .code(DATA_IS_NOT_VALID)
                        .message("User already exists")
                        .get();
            }

            repository.insert(keycloakUserId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public Long getPrimaryUserIdBy(String keycloakUserId) {
        try {
            return repository.findPrimaryUserIdBy(keycloakUserId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }
}
