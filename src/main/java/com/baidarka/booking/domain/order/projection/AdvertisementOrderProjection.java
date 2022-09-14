package com.baidarka.booking.domain.order.projection;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.model.OrderStatus;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AdvertisementOrderProjection {
    Long id;
    OrderStatus orderStatus;
    Integer seat;
    LocalDateTime arrival;
    LocalDateTime departure;
    PrimaryUserProjection primaryUser;
    AdvertisementProjection advertisement;
}
