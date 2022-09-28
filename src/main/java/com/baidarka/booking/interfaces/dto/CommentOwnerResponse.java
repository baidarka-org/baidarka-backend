package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentOwnerResponse {
    String firstName;
    String lastName;
    PrimaryUserResponse primaryUser;
}
