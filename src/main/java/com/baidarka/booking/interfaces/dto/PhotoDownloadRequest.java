package com.baidarka.booking.interfaces.dto;

import com.amazonaws.HttpMethod;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PhotoDownloadRequest {
    String id;
    HttpMethod method;
}
