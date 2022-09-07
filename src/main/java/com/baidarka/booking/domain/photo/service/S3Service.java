package com.baidarka.booking.domain.photo.service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.net.URL;

public interface S3Service {
    void upload(PutObjectRequest request, String keycloakUserId);
    URL download(GeneratePresignedUrlRequest request);

    void delete(DeleteObjectRequest request);
}
