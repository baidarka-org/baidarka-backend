package com.baidarka.booking.interfaces.dto;

import lombok.Builder;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Value
@Builder
public class AdvertisementPhotoRequest {
    String keycloakUserId;
    String advertisementId;
    List<MultipartFile> photos;
}
