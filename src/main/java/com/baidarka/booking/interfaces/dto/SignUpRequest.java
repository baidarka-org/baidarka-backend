package com.baidarka.booking.interfaces.dto;

import lombok.Value;

@Value
public class SignUpRequest {
    PrimaryUserRequest primaryUser;
    PrimaryUserPhotoRequest primaryUserPhoto;
}
