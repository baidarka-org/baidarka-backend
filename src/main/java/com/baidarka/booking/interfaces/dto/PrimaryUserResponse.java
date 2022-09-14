package com.baidarka.booking.interfaces.dto;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Value;

@Value
public class PrimaryUserResponse {
    Long id;
}
