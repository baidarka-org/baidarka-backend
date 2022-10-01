package com.baidarka.booking.application.operation;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.domain.photo.service.S3Service;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.infrastructure.utility.S3Property;
import com.baidarka.booking.interfaces.adapter.PrimaryUserPhotoRepositoryAdapter;
import com.baidarka.booking.interfaces.dto.PhotoDownloadRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadResponse;
import com.baidarka.booking.interfaces.dto.PhotoRequest;
import com.baidarka.booking.interfaces.facade.PhotoConversationFacade;
import com.baidarka.booking.interfaces.mapper.PresignedUrlToDownloadPhotoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static com.baidarka.booking.infrastructure.model.PhotoType.PRIMARY_USER;

@Component
@RequiredArgsConstructor
public class PrimaryUserPhotoOperation implements PhotoOperation<PhotoRequest, PhotoDownloadResponse, String>{
    private final S3Property property;
    private final S3Service service;
    private final PhotoConversationFacade facade;
    private final PrimaryUserPhotoRepositoryAdapter adapter;

    @Override
    public void upload(PhotoRequest request) {
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
    public PhotoDownloadResponse download(PhotoDownloadRequest request) {
        final var key = adapter.findKeyBy(request.getId());

        final var generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(property.getBucketName(), key, request.getMethod());

        generatePresignedUrlRequest.withExpiration(getAsDate());

        return PresignedUrlToDownloadPhotoResponseMapper.MAPPER.mapFrom(
                service.download(generatePresignedUrlRequest, PRIMARY_USER),
                generatePresignedUrlRequest);
    }

    @Override
    public void delete(String keycloakUserId) {
        final var deleteObjectRequest =
                new DeleteObjectRequest(
                        property.getBucketName(), adapter.findKeyBy(keycloakUserId));

        service.delete(deleteObjectRequest, PRIMARY_USER);
    }
}
