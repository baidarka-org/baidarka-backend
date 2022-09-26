package com.baidarka.booking.infrastructure.event;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.model.NotificationType;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Arrays;

@Getter
public class NotificationReadyEvent extends ApplicationEvent {
    private final NotificationType notificationType;
    private final PrimaryUserProjection primaryUser;
    private final String[] args;

    public NotificationReadyEvent(Object source,
                                  NotificationType notificationType,
                                  PrimaryUserProjection primaryUser,
                                  String... args) {
        super(source);
        this.notificationType = notificationType;
        this.primaryUser = primaryUser;
        this.args = Arrays.stream(args).toArray(String[]::new);
    }
}
