package com.baidarka.booking.interfaces.facade;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.baidarka.booking.infrastructure.utility.S3Property;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;
import static org.apache.commons.io.FilenameUtils.getName;
import static org.apache.http.HttpHeaders.CONTENT_LENGTH;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

@Component
@RequiredArgsConstructor
public class PhotoConversationFacade {
    private final S3Property property;

    @SneakyThrows
    public PutObjectRequest convert(MultipartFile photo, String keycloakUserId) {
        final Map<String, String> metadata = new HashMap<>();

        metadata.put(CONTENT_TYPE, photo.getContentType());
        metadata.put(CONTENT_LENGTH, String.valueOf(photo.getSize()));

        return new PutObjectRequest(
                property.getBucketName(),
                getKeyBy(photo.getOriginalFilename(), keycloakUserId),
                photo.getInputStream(),
                getObjectMetadata(metadata));
    }

    @SneakyThrows
    public PutObjectRequest convert(MultipartFile photo, String keycloakUserId, String advertisementId) {
        final Map<String, String> metadata = new HashMap<>();

        metadata.put(CONTENT_TYPE, photo.getContentType());
        metadata.put(CONTENT_LENGTH, String.valueOf(photo.getSize()));

        return new PutObjectRequest(
                property.getBucketName(),
                getKeyBy(photo.getOriginalFilename(), keycloakUserId, advertisementId),
                photo.getInputStream(),
                getObjectMetadata(metadata));
    }
    private ObjectMetadata getObjectMetadata(final Map<String, String> metadata) {
        final var objectMetadata = new ObjectMetadata();

        metadata.forEach(objectMetadata::addUserMetadata);

        return objectMetadata;
    }

    private String getEncryptedPhotoNameBy(String photoName) {
        return String.format("%d-%s", photoName.hashCode(), randomUUID());
    }

    private String getKeyBy(String photoName, String keycloakUserId, String advertisementId) {
        final var originalPhotoName = getName(photoName);

        return String.format("%d/%d/%s",
                keycloakUserId.hashCode(),
                advertisementId.hashCode(),
                getEncryptedPhotoNameBy(originalPhotoName));
    }

    private String getKeyBy(String photoName, String keycloakUserId) {
        final var originalPhotoName = getName(photoName);

        return String.format("%d/%s",
                keycloakUserId.hashCode(),
                getEncryptedPhotoNameBy(originalPhotoName));
    }
}
