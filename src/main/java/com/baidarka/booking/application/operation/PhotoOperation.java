package com.baidarka.booking.application.operation;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.domain.photo.service.S3Service;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.infrastructure.utility.ExpirationDateFactory;
import com.baidarka.booking.infrastructure.utility.S3Property;
import com.baidarka.booking.interfaces.adapter.PrimaryUserPhotoRepositoryAdapter;
import com.baidarka.booking.interfaces.dto.DownloadPrimaryUserPhotoRequest;
import com.baidarka.booking.interfaces.dto.DownloadPrimaryUserPhotoResponse;
import com.baidarka.booking.interfaces.dto.UploadPrimaryUserPhotoRequest;
import com.baidarka.booking.interfaces.facade.PhotoConversationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.baidarka.booking.infrastructure.config.EhCacheConfig.CACHE;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;
@Component
@RequiredArgsConstructor
public class PhotoOperation {
    private final S3Property property;
    private final S3Service service;
    private final PhotoConversationFacade facade;
    private final PrimaryUserPhotoRepositoryAdapter adapter;

    public void upload(UploadPrimaryUserPhotoRequest request) {
        final var photo = request.getPhoto();

        final var contentType = Objects.requireNonNull(
                photo.getContentType(), "Photo type is not found");

        if (
                !contentType.equals(IMAGE_PNG.getMimeType()) &&
                !contentType.equals(IMAGE_JPEG.getMimeType())) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message("Content type must be png or jpeg")
                    .get();
        }

        final var putObjectRequest = facade.convert(photo, request.getKeycloakUserId());

        service.upload(putObjectRequest, request.getKeycloakUserId());
    }

    @CacheEvict(
            cacheNames = CACHE,
            key = "#keycloakUserId")
    public void delete(String keycloakUserId) {
        final var deleteObjectRequest =
                new DeleteObjectRequest(
                        property.getBucketName(), adapter.findKeyBy(keycloakUserId));

        service.delete(deleteObjectRequest);
    }

    @Cacheable(
            cacheNames = CACHE,
            key = "#request.keycloakUserId")
    public DownloadPrimaryUserPhotoResponse download(DownloadPrimaryUserPhotoRequest request) {
        final var key = adapter.findKeyBy(request.getKeycloakUserId());

        final var generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(
                        property.getBucketName(), key, request.getMethod());

        generatePresignedUrlRequest.withExpiration(ExpirationDateFactory.getAsDate(1));
        
        return DownloadPrimaryUserPhotoResponse.builder()
                .photoURL(service
                        .download(generatePresignedUrlRequest)
                        .toString())
                .expiresAt(generatePresignedUrlRequest
                        .getExpiration()
                        .toInstant())
                .build();
    }
}
