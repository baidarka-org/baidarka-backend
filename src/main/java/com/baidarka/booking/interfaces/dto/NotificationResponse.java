package com.baidarka.booking.interfaces.dto;

import com.baidarka.booking.infrastructure.model.NotificationType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class NotificationResponse {
    NotificationType notificationType;
    LocalDateTime pushedAt;
}
