package com.baidarka.booking.interfaces.facade;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.baidarka.booking.infrastructure.utility.S3Property;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static java.util.UUID.randomUUID;
import static org.apache.http.HttpHeaders.CONTENT_LENGTH;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

@Component
@RequiredArgsConstructor
public class PhotoConversationFacade {
    private final S3Property s3Property;

    @SneakyThrows
    public PutObjectRequest convert(MultipartFile photo, String keycloakUserId) {
        final Map<String, String> metadata = new HashMap<>();

        metadata.put(CONTENT_TYPE, photo.getContentType());
        metadata.put(CONTENT_LENGTH, String.valueOf(photo.getSize()));

        return new PutObjectRequest(
                s3Property.getBucketName(),
                getKeyBy(photo.getOriginalFilename(), keycloakUserId),
                photo.getInputStream(),
                getObjectMetadata(metadata));
    }

    private ObjectMetadata getObjectMetadata(final Map<String, String> metadata) {
        final var objectMetadata = new ObjectMetadata();

        metadata.forEach(objectMetadata::addUserMetadata);

        return objectMetadata;
    }

    private String getKeyBy(String photoName, String keycloakUserId) {
        final var originalPhotoName = FilenameUtils.getName(photoName);

        return String.format("%d/%s",
                keycloakUserId.hashCode(),
                getEncryptedPhotoNameBy(originalPhotoName));
    }

    private String getEncryptedPhotoNameBy(String photoName) {
        return String.format("%d-%s", photoName.hashCode(), randomUUID());
    }
}
