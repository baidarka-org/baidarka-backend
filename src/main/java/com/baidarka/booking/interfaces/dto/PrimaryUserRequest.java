package com.baidarka.booking.interfaces.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class PrimaryUserRequest {
    UUID keycloakUserId;
}
