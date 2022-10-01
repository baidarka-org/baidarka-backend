package com.baidarka.booking.infrastructure.utility;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class DateConverter {
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date pushedAt) {
        return new Timestamp(
                pushedAt.getTime())
                .toLocalDateTime();
    }
}
