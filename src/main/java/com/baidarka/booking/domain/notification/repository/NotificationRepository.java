package com.baidarka.booking.domain.notification.repository;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import com.baidarka.booking.infrastructure.model.NotificationType;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends Repository<NotificationProjection, Long> {

    @Modifying
    @Query(value = """
                    INSERT INTO notification
                                (notification_type, attributes, primary_user_id)
                                    VALUES
                                (:notificationType, CAST(:attributes AS JSONB), :primaryUserId)
                    """)
    void insert(@Param("notificationType") NotificationType notificationType,
                @Param("attributes") String attributes,
                @Param("primaryUserId") Long primaryUserId);

    @Query(value = """
                    SELECT notification_type, attributes, primary_user_id, pushed_at
                            FROM notification
                            WHERE primary_user_id = :primaryUserId
                    """,
            rowMapperClass = NotificationRowMapper.class)
    List<NotificationProjection> findNotificationBy(@Param("primaryUserId") Long primaryUserId);
}
