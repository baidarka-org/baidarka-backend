package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import com.baidarka.booking.domain.notification.repository.NotificationRepository;
import com.baidarka.booking.domain.notification.service.NotificationService;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.infrastructure.exception.DataAccessException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.baidarka.booking.application.service.PrimaryUserServiceImpl.ACCESS_DENIED;
import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_DENIED;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;
    private final PrimaryUserService service;

    @Override
    public void save(NotificationProjection notification) {
        final var gson = new Gson();

        try {
            repository.insert(
                    notification.getNotificationType(),
                    gson.toJson(notification.getAttributes()),
                    notification.getPrimaryUser().getId());
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }

    @Override
    public List<NotificationProjection> getNotificationsBy(String keycloakUserId) {
        final var primaryUserId = service.getPrimaryUserIdBy(keycloakUserId);

        try {
            return repository.findNotificationBy(primaryUserId);
        } catch (DataAccessException dae) {
            throw factory()
                    .code(DATA_ACCESS_DENIED)
                    .message(ACCESS_DENIED)
                    .get();
        }
    }
}
