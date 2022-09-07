package com.baidarka.booking.domain.notification.projection;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.model.NotificationType;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class NotificationProjection {
    Long id;
    NotificationType notificationType;
    PrimaryUserProjection primaryUser;
    LocalDateTime pushedAt;
}
