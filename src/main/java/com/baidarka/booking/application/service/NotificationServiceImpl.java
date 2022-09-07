package com.baidarka.booking.application.service;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import com.baidarka.booking.domain.notification.repository.NotificationRepository;
import com.baidarka.booking.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;

    @Override
    public void save(NotificationProjection notification) {

    }

    @Override
    public List<NotificationProjection> getBy(String keycloakUserId) {
        return null;
    }
}
