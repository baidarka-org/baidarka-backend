package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.notification.service.NotificationService;
import com.baidarka.booking.interfaces.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.baidarka.booking.interfaces.dto.NotificationResponse.builder;

@Component
@RequiredArgsConstructor
public class NotificationOperation {
    private final NotificationService service;

    public List<NotificationResponse> get(String keycloakUserId) {
        return service.getNotificationsBy(keycloakUserId).stream()
                .map(notification ->
                        builder()
                                .notificationType(notification.getNotificationType())
                                .attributes(notification.getAttributes())
                                .pushedAt(notification.getPushedAt())
                                .build())
                .toList();
    }
}
