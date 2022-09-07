package com.baidarka.booking.infrastructure.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class ExpirationDateFactory {
    public static Date getAsDate(int hour) {
        final var expiresAt =
                LocalDateTime.now().plusHours(hour);

        return Timestamp.valueOf(expiresAt);
    }
}
