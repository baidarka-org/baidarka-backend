package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.notification.service.NotificationService;
import com.baidarka.booking.interfaces.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationOperation {
    private final NotificationService service;

    public List<NotificationResponse> get(String keycloakUserId) {
        return service.getBy(keycloakUserId).stream()
                .map(s -> {
                    final var notificationType =
                            s.getNotificationType();
                    final var pushedAt =
                            s.getPushedAt();

                    return NotificationResponse.builder()
                            .notificationType(notificationType)
                            .pushedAt(pushedAt)
                            .build();
                })
                .toList();
    }
}
