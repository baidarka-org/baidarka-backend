package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdvertisementOwnerResponse {
    String firstName;
    String lastName;
    String phoneNumber;
    PrimaryUserResponse primaryUser;
}
