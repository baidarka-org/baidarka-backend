package com.baidarka.booking.interfaces.mapper;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.interfaces.dto.DownloadPhotoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.net.URL;

@Mapper
public interface PresignedUrlToDownloadPhotoResponse {
    PresignedUrlToDownloadPhotoResponse MAPPER =
            Mappers.getMapper(PresignedUrlToDownloadPhotoResponse.class);

    @Mapping(target = "presignedUrl", expression = "java(presignedUrl.toString())")
    @Mapping(target = "expiresAt", expression = """
                                                    java(request
                                                                .getExpiration()
                                                                .toInstant())
                                                """)
    DownloadPhotoResponse mapFrom(URL presignedUrl, GeneratePresignedUrlRequest request);
}
