package com.baidarka.booking.domain.notification.repository;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import org.springframework.data.repository.Repository;

public interface NotificationRepository extends Repository<NotificationProjection, Long> {
}
