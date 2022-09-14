package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
@Builder
public class UploadMultiplePhotoRequest {
    String keycloakUserId;
    String advertisementId;
    MultipartFile photo;
}
