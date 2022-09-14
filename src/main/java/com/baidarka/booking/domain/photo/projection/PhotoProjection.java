package com.baidarka.booking.domain.photo.projection;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder
public class PhotoProjection {
    UUID id;
    String key;
    boolean isDefault;
    @Transient MultipartFile photo;
    Instant uploadedAt;
}
