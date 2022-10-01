package com.baidarka.booking.application.operation;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.domain.photo.service.S3Service;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.infrastructure.model.PhotoType;
import com.baidarka.booking.infrastructure.utility.S3Property;
import com.baidarka.booking.interfaces.adapter.AdvertisementPhotoRepositoryAdapter;
import com.baidarka.booking.interfaces.adapter.PrimaryUserPhotoRepositoryAdapter;
import com.baidarka.booking.interfaces.dto.*;
import com.baidarka.booking.interfaces.facade.PhotoConversationFacade;
import com.baidarka.booking.interfaces.mapper.PresignedUrlToDownloadPhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static com.baidarka.booking.infrastructure.config.EhCacheConfig.CACHE;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static com.baidarka.booking.infrastructure.model.PhotoType.ADVERTISEMENT;
import static com.baidarka.booking.infrastructure.model.PhotoType.PRIMARY_USER;
import static com.baidarka.booking.infrastructure.utility.ExpirationDateFactory.getAsDate;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;
@Component
@RequiredArgsConstructor
public class PhotoOperation {
    private final S3Property property;
    private final S3Service service;
    private final PhotoConversationFacade facade;
    private final PrimaryUserPhotoRepositoryAdapter primaryUserPhotoAdapter;
    private final AdvertisementPhotoRepositoryAdapter advertisementPhotoAdapter;

    public void primaryUserUpload(UploadPhotoRequest request) {
        final var photo = request.getPhoto();

        if (isValid(photo)) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message("Content type must be png or jpeg")
                    .get();
        }

        final var putObjectRequest = facade.convert(photo, request.getKeycloakUserId());

        service.upload(putObjectRequest, request.getKeycloakUserId(), PRIMARY_USER);
    }

    public void advertisementUpload(UploadMultiplePhotoRequest request) {
        final var photo = request.getPhoto();

        if (!isValid(photo)) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message("Content type must be png or jpeg")
                    .get();
        }

        final var putObjectRequest =
                facade.convert(photo,
                        request.getKeycloakUserId(),
                        request.getAdvertisementId());

        service.upload(putObjectRequest, request.getAdvertisementId(), ADVERTISEMENT);
    }

    @CacheEvict(
            cacheNames = CACHE,
            key = "#keycloakUserId")
    public void primaryUserDelete(String keycloakUserId) {
        final var deleteObjectRequest =
                new DeleteObjectRequest(
                        property.getBucketName(), primaryUserPhotoAdapter.findKeyBy(keycloakUserId));

        service.delete(deleteObjectRequest, PRIMARY_USER);
    }

    @CacheEvict(
            cacheNames = CACHE,
            key = "#request.advertisementId"
    )
    public void advertisementDelete(AdvertisementDeleteRequest request) {
        final var deleteObjectRequest =
                new DeleteObjectRequest(
                        property.getBucketName(),
                        advertisementPhotoAdapter.findKeyBy(request.getPhotoId()));

        service.delete(deleteObjectRequest, ADVERTISEMENT);
    }

    @Cacheable(
            cacheNames = CACHE,
            key = "#request.id")
    public DownloadPhotoResponse primaryUserDownload(DownloadPhotoRequest request) {
        final var key = primaryUserPhotoAdapter.findKeyBy(request.getId());

        final var generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(property.getBucketName(), key, request.getMethod());

        generatePresignedUrlRequest.withExpiration(getAsDate(1));

        return PresignedUrlToDownloadPhotoResponse.MAPPER.mapFrom(
                service.download(generatePresignedUrlRequest, PRIMARY_USER),
                generatePresignedUrlRequest);
    }

    @Cacheable(
            cacheNames = CACHE,
            key = "#request.id")
    public List<DownloadPhotoResponse> advertisementDownload(DownloadPhotoRequest request) {
        final var keys = advertisementPhotoAdapter.findKeysBy(request.getId());

        return keys.stream()
                .map(key -> {
                    final var generatePresignedUrlRequest =
                            new GeneratePresignedUrlRequest(property.getBucketName(), key, request.getMethod());

                    generatePresignedUrlRequest.withExpiration(getAsDate(1));

                    return generatePresignedUrlRequest;
                })
                .map(presignedUrl ->
                        PresignedUrlToDownloadPhotoResponse.MAPPER.mapFrom(
                                service.download(presignedUrl, ADVERTISEMENT), presignedUrl))
                .toList();
    }

    private boolean isValid(MultipartFile photo) {
        final var contentType = Objects.requireNonNull(
                photo.getContentType(), "Photo type is not found");

        return contentType.equals(IMAGE_PNG.getMimeType()) ||
                contentType.equals(IMAGE_JPEG.getMimeType());
    }
}
