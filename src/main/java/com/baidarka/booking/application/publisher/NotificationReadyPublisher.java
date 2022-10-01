package com.baidarka.booking.application.publisher;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.event.NotificationReadyEvent;
import com.baidarka.booking.infrastructure.model.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationReadyPublisher {
    private final ApplicationEventPublisher publisher;

    public void publish(NotificationType notificationType,
                        PrimaryUserProjection primaryUser,
                        String... args) {

        publisher.publishEvent(
                new NotificationReadyEvent(this,
                        notificationType, primaryUser, args));
    }
}
