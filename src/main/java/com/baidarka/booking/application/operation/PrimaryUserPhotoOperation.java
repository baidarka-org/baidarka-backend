package com.baidarka.booking.application.operation;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.domain.photo.service.S3Service;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.infrastructure.utility.S3Property;
import com.baidarka.booking.interfaces.adapter.PrimaryUserPhotoRepositoryAdapter;
import com.baidarka.booking.interfaces.dto.DownloadPhotoRequest;
import com.baidarka.booking.interfaces.dto.DownloadPhotoResponse;
import com.baidarka.booking.interfaces.dto.UploadPhotoRequest;
import com.baidarka.booking.interfaces.facade.PhotoConversationFacade;
import com.baidarka.booking.interfaces.mapper.PresignedUrlToDownloadPhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import static com.baidarka.booking.infrastructure.config.EhCacheConfig.CACHE;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static com.baidarka.booking.infrastructure.model.PhotoType.PRIMARY_USER;

@Component
@RequiredArgsConstructor
public class PrimaryUserPhotoOperation implements PhotoOperation<UploadPhotoRequest, DownloadPhotoResponse, String>{
    private final S3Property property;
    private final S3Service service;
    private final PhotoConversationFacade facade;
    private final PrimaryUserPhotoRepositoryAdapter adapter;

    @Override
    public void upload(UploadPhotoRequest request) {
        final var photo = request.getPhoto();

        if (isValid(photo)) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message(INVALID_CONTENT)
                    .get();
        }

        final var putObjectRequest = facade.convert(photo, request.getKeycloakUserId());

        service.upload(putObjectRequest, request.getKeycloakUserId(), PRIMARY_USER);
    }

    @Override
    @Cacheable(
            cacheNames = CACHE,
            key = "#request.id")
    public DownloadPhotoResponse download(DownloadPhotoRequest request) {
        final var key = adapter.findKeyBy(request.getId());

        final var generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(property.getBucketName(), key, request.getMethod());

        generatePresignedUrlRequest.withExpiration(getAsDate());

        return PresignedUrlToDownloadPhotoResponse.MAPPER.mapFrom(
                service.download(generatePresignedUrlRequest, PRIMARY_USER),
                generatePresignedUrlRequest);
    }

    @Override
    @CacheEvict(
            cacheNames = CACHE,
            key = "#keycloakUserId")
    public void delete(String keycloakUserId) {
        final var deleteObjectRequest =
                new DeleteObjectRequest(
                        property.getBucketName(), adapter.findKeyBy(keycloakUserId));

        service.delete(deleteObjectRequest, PRIMARY_USER);
    }
}
