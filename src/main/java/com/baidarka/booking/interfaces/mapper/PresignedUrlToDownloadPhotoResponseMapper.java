package com.baidarka.booking.interfaces.mapper;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.net.URL;

@Mapper
public interface PresignedUrlToDownloadPhotoResponseMapper {
    PresignedUrlToDownloadPhotoResponseMapper MAPPER =
            Mappers.getMapper(PresignedUrlToDownloadPhotoResponseMapper.class);

    @Mapping(target = "presignedUrl", expression = "java(presignedUrl.toString())")
    @Mapping(target = "expiresAt", expression = """
                                                    java(request
                                                                .getExpiration()
                                                                .toInstant())
                                                """)
    PhotoDownloadResponse mapFrom(URL presignedUrl, GeneratePresignedUrlRequest request);
}
