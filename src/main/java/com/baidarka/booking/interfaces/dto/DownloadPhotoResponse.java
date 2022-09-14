package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class DownloadPhotoResponse {
    String presignedUrl;
    Instant expiresAt;
}
