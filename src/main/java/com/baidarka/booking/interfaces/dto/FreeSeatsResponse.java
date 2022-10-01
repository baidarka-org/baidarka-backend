package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class FreeSeatsResponse {
    Integer seat;
}
