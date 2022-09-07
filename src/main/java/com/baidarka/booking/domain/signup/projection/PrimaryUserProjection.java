package com.baidarka.booking.domain.signup.projection;

import lombok.Value;

import java.util.UUID;

@Value
public class PrimaryUserProjection {
    Long id;
    UUID keycloakUserId;
}
