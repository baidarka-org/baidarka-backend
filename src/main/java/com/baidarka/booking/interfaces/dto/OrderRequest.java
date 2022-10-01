package com.baidarka.booking.interfaces.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class OrderRequest {
    UUID advertisementId;
    Integer seat;
    LocalDateTime arrival;
    LocalDateTime departure;
}
