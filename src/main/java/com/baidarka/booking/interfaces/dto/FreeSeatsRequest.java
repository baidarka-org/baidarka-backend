package com.baidarka.booking.interfaces.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class FreeSeatsRequest {
    UUID advertisementId;
    LocalDateTime date;
}
