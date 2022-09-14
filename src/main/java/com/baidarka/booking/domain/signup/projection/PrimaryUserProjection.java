package com.baidarka.booking.domain.signup.projection;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class PrimaryUserProjection {
    Long id;
    UUID keycloakUserId;
}
