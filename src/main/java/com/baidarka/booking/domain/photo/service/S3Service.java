package com.baidarka.booking.domain.photo.service;

import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.baidarka.booking.infrastructure.model.PhotoType;

import java.net.URL;

public interface S3Service {
    void upload(PutObjectRequest request, String id, PhotoType photoType);
    URL download(GeneratePresignedUrlRequest request, PhotoType photoType);
    void delete(DeleteObjectRequest request, PhotoType photoType);
}
