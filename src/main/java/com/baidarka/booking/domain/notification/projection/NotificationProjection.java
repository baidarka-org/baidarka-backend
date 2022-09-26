package com.baidarka.booking.domain.notification.projection;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.model.NotificationType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class NotificationProjection {
    Long id;
    NotificationType notificationType;
    PrimaryUserProjection primaryUser;
    Map<Integer, String> attributes;
    LocalDateTime pushedAt;
}
