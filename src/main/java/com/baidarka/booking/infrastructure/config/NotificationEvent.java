package com.baidarka.booking.infrastructure.config;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {
    @Getter
    private final NotificationProjection notification;

    public NotificationEvent(Object source, NotificationProjection notification) {
        super(source);
        this.notification = notification;
    }
}
