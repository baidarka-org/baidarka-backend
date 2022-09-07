package com.baidarka.booking.interfaces.dto;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

@Value
public class PrimaryUserPhotoRequest {
    MultipartFile photo;
}
