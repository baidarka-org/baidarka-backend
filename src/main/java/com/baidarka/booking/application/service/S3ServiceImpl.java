package com.baidarka.booking.application.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.baidarka.booking.domain.photo.projection.PhotoProjection;
import com.baidarka.booking.domain.photo.service.S3Service;
import com.baidarka.booking.infrastructure.model.PhotoType;
import com.baidarka.booking.interfaces.interpreter.PhotoInterpreter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URL;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.MEDIA_OPERATION_FAILED;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final TransferManager transferManager;
    private final PhotoInterpreter interpreter;

    @Async
    @Override
    @SneakyThrows
    public void upload(PutObjectRequest request, String id, PhotoType photoType) {
        request.withGeneralProgressListener(progressEvent -> {
            switch (progressEvent.getEventType()) {
                case CLIENT_REQUEST_FAILED_EVENT ->
                        throw factory()
                                .code(MEDIA_OPERATION_FAILED)
                                .message("Media file uploading process failed")
                                .get();
                case CLIENT_REQUEST_SUCCESS_EVENT -> {
                    final var projection =
                            PhotoProjection.builder()
                                    .id(randomUUID())
                                    .key(request.getKey())
                                    .build();

                    interpreter.doServiceTask(photoType).save(projection, id);
                }
            }
        });

        transferManager
                .upload(request)
                .waitForUploadResult();
    }

    @Override
    public URL download(GeneratePresignedUrlRequest request, PhotoType photoType) {
        try {
            return transferManager
                    .getAmazonS3Client()
                    .generatePresignedUrl(request);
        } catch (AmazonServiceException ase) {
            throw factory()
                    .code(MEDIA_OPERATION_FAILED)
                    .message("Media file URL generating process failed")
                    .get();
        }
    }

    @Override
    public void delete(DeleteObjectRequest request, PhotoType photoType) {
        try {
            transferManager
                    .getAmazonS3Client()
                    .deleteObject(request);

            interpreter.doServiceTask(photoType).deletePhotoBy(request.getKey());
        } catch (AmazonServiceException ase) {
            throw factory()
                    .code(MEDIA_OPERATION_FAILED)
                    .message("Media file deleting process failed")
                    .get();
        }
    }
}
