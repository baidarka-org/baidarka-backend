package com.baidarka.booking.domain.notification.service;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;

import java.util.List;

public interface NotificationService {
    void save(NotificationProjection notification);
    List<NotificationProjection> getNotificationsBy(String keycloakUserId);
}
