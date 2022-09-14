package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdvertisementDeleteRequest {
    String advertisementId;
    String keycloakUserId;
    String photoId;
}
