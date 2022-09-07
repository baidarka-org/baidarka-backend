package com.baidarka.booking.infrastructure.config;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(final NotificationProjection notification) {
        publisher.publishEvent(new NotificationEvent(this, notification));

        log.debug("Sent {} notification event", notification.getNotificationType());
    }
}
