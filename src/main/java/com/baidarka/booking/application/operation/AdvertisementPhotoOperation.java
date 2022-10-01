package com.baidarka.booking.application.operation;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.baidarka.booking.domain.advertisement.service.AdvertisementService;
import com.baidarka.booking.domain.photo.service.S3Service;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.infrastructure.exception.ExceptionFactory;
import com.baidarka.booking.infrastructure.utility.S3Property;
import com.baidarka.booking.interfaces.adapter.AdvertisementPhotoRepositoryAdapter;
import com.baidarka.booking.interfaces.dto.AdvertisementDeleteRequest;
import com.baidarka.booking.interfaces.dto.AdvertisementPhotoRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadResponse;
import com.baidarka.booking.interfaces.facade.PhotoConversationFacade;
import com.baidarka.booking.interfaces.mapper.PresignedUrlToDownloadPhotoResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_FORBIDDEN;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_IS_NOT_VALID;
import static com.baidarka.booking.infrastructure.model.PhotoType.ADVERTISEMENT;

@Component
@RequiredArgsConstructor
public class AdvertisementPhotoOperation implements PhotoOperation<AdvertisementPhotoRequest, List<PhotoDownloadResponse>, AdvertisementDeleteRequest> {
    public final static String NOT_ADVERTISEMENT_OWNER = "You are not owned this advertisement";

    private final S3Property property;
    private final S3Service s3Service;
    private final AdvertisementService advertisementService;
    private final PrimaryUserService primaryUserService;
    private final PhotoConversationFacade facade;
    private final AdvertisementPhotoRepositoryAdapter adapter;

    @Override
    public void upload(AdvertisementPhotoRequest request) {
        if (checkIfOwnerBy(request.getKeycloakUserId(), UUID.fromString(request.getAdvertisementId()))) {
            throw ExceptionFactory.factory()
                    .code(DATA_ACCESS_FORBIDDEN)
                    .message(NOT_ADVERTISEMENT_OWNER)
                    .get();
        }

        AtomicBoolean hasInvalidPhotos = new AtomicBoolean(false);

        request.getPhotos().stream()
                .filter(photo -> {
                    if (isValid(photo)) {
                        hasInvalidPhotos.set(true);

                        return false;
                    } else {
                        return true;
                    }})
                .map(photo ->
                        facade.convert(photo,
                                request.getKeycloakUserId(),
                                request.getAdvertisementId()))
                .forEach(putObjectRequest ->
                            s3Service.upload(putObjectRequest,
                                    request.getAdvertisementId(),
                                    ADVERTISEMENT));

        if (hasInvalidPhotos.get()) {
            throw ExceptionFactory.factory()
                    .code(DATA_IS_NOT_VALID)
                    .message(INVALID_CONTENT)
                    .get();
        }
    }

    @Override
    public List<PhotoDownloadResponse> download(PhotoDownloadRequest request) {
        final var keys = adapter.findKeysBy(request.getId());

        return keys.stream()
                .map(key -> {
                    final var generatePresignedUrlRequest =
                            new GeneratePresignedUrlRequest(property.getBucketName(), key, request.getMethod());

                    generatePresignedUrlRequest.withExpiration(getAsDate());

                    final var presignedUrl = s3Service.download(generatePresignedUrlRequest, ADVERTISEMENT);

                    return PresignedUrlToDownloadPhotoResponseMapper.MAPPER
                            .mapFrom(presignedUrl, generatePresignedUrlRequest);
                })
                .toList();
    }

    @Override
    public void delete(AdvertisementDeleteRequest request) {
        if (checkIfOwnerBy(request.getKeycloakUserId(), request.getAdvertisementId())) {
             throw ExceptionFactory.factory()
                     .code(DATA_ACCESS_FORBIDDEN)
                     .message(NOT_ADVERTISEMENT_OWNER)
                     .get();
        }

        final var deleteObjectRequest =
                new DeleteObjectRequest(
                        property.getBucketName(),
                        adapter.findKeyBy(request.getPhotoId()));

        s3Service.delete(deleteObjectRequest, ADVERTISEMENT);
    }

    private boolean checkIfOwnerBy(String keycloakUserId, UUID advertisementId) {
        final var isOwner =
                advertisementService.isOwnerBy(
                        primaryUserService.getPrimaryUserIdBy(keycloakUserId),
                        advertisementId);

        return !isOwner;
    }
}
