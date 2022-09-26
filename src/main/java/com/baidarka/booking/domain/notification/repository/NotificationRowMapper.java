package com.baidarka.booking.domain.notification.repository;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static com.baidarka.booking.infrastructure.model.NotificationType.valueOf;

public class NotificationRowMapper implements RowMapper<NotificationProjection> {
    @Override
    public NotificationProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        final var gson = new Gson();

        final var type = new TypeToken<Map<Integer, String>>() {}.getType();

        return NotificationProjection.builder()
                .notificationType(valueOf(rs.getString("notification_type")))
                .attributes(gson.fromJson(rs.getString("attributes"), type))
                .pushedAt(convertToLocalDateTimeViaInstant(rs.getDate("pushed_at")))
                .build();
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date pushedAt) {
        return new Timestamp(
                pushedAt.getTime())
                .toLocalDateTime();
    }
}
