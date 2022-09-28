package com.baidarka.booking.application.operation;

import com.baidarka.booking.interfaces.dto.DownloadPhotoRequest;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static org.apache.http.entity.ContentType.IMAGE_JPEG;
import static org.apache.http.entity.ContentType.IMAGE_PNG;

public interface PhotoOperation<UploadType, DownloadResultType, DeleteType> {
    int PHOTO_EXPIRATION_HOURS = 1;
    String INVALID_CONTENT = "Content type must be png or jpeg";

    void upload(UploadType uploadType);
    DownloadResultType download(DownloadPhotoRequest request);
    void delete(DeleteType deleteType);

    default boolean isValid(MultipartFile photo) {
        final var contentType = Objects.requireNonNull(
                photo.getContentType(), "Photo type is not found");

        return !contentType.equals(IMAGE_PNG.getMimeType()) &&
                !contentType.equals(IMAGE_JPEG.getMimeType());
    }

    default Date getAsDate() {
        final var expiresAt =
                LocalDateTime.now().plusHours(PHOTO_EXPIRATION_HOURS);

        return Timestamp.valueOf(expiresAt);
    }
}
