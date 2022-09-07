package com.baidarka.booking.application.listener;

import com.baidarka.booking.domain.notification.service.NotificationService;
import com.baidarka.booking.infrastructure.config.NotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final NotificationService service;

    @EventListener
    public void onApplicationEvent(NotificationEvent event) {
        service.save(event.getNotification());

        log.debug("Received event {}", event);
    }
}
