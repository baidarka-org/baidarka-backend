package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import com.baidarka.booking.domain.notification.repository.NotificationRepository;
import com.baidarka.booking.domain.notification.service.NotificationService;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;
    private final PrimaryUserService service;

    @Override
    public void save(NotificationProjection notification) {
        final var gson = new Gson();

        repository.insert(
                notification.getNotificationType(),
                gson.toJson(notification.getAttributes()),
                notification.getPrimaryUser().getId());
    }

    @Override
    public List<NotificationProjection> getBy(String keycloakUserId) {
        final var primaryUserId = service.getPrimaryUserIdBy(keycloakUserId);

        return repository.findBy(primaryUserId);
    }
}
