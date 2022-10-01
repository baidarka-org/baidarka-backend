package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class AdvertisementDeleteRequest {
    UUID advertisementId;
    String keycloakUserId;
    String photoId;
}
