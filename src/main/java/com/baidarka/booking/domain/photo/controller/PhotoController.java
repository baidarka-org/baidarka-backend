package com.baidarka.booking.domain.photo.controller;

import com.amazonaws.HttpMethod;
import com.baidarka.booking.application.operation.PhotoOperation;
import com.baidarka.booking.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.model.PhotoType.ADVERTISEMENT;
import static com.baidarka.booking.infrastructure.model.PhotoType.PRIMARY_USER;
import static com.baidarka.booking.infrastructure.utility.RoleExpression.AUTHENTICATED;

@RestController
@RequestMapping("api/v1/photo")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoOperation operation;

    @PutMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> update(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                       @RequestParam MultipartFile photo) {
        final var request =
                UploadPhotoRequest.builder()
                        .keycloakUserId(principal.getName())
                        .photo(photo)
                        .build();

        operation.primaryUserUpload(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> delete(@AuthenticationPrincipal KeycloakPrincipal<?> principal) {
        operation.primaryUserDelete(principal.getName());

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<DownloadPhotoResponse> download(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                                          HttpServletRequest servlet) {
        final var request =
                DownloadPhotoRequest.builder()
                        .id(principal.getName())
                        .method(HttpMethod.valueOf(servlet.getMethod()))
                        .build();

        return ResponseEntity
                .ok()
                .body(operation.primaryUserDownload(request));
    }

    @PutMapping("{advertisementId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> upload(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                       @PathVariable String advertisementId,
                                       @RequestParam List<MultipartFile> photos) {
        photos.stream()
                .map(photo ->
                        UploadMultiplePhotoRequest.builder()
                                .keycloakUserId(principal.getName())
                                .advertisementId(advertisementId)
                                .photo(photo)
                                .build())
                .forEach(operation::advertisementUpload);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("{advertisementId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<List<DownloadPhotoResponse>> multipleDownload(@PathVariable String advertisementId,
                                                                        HttpServletRequest servlet) {
        final var request =
                DownloadPhotoRequest.builder()
                        .id(advertisementId)
                        .method(HttpMethod.valueOf(servlet.getMethod()))
                        .build();

        return ResponseEntity
                .ok()
                .body(operation.advertisementDownload(request));
    }

    @DeleteMapping("{advertisementId}/{photoId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> advertisementDelete(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                                    @PathVariable String advertisementId,
                                                    @PathVariable String photoId) {
        final var request =
                AdvertisementDeleteRequest.builder()
                        .advertisementId(advertisementId)
                        .photoId(photoId)
                        .keycloakUserId(principal.getName())
                        .build();

        operation.advertisementDelete(request);

        return ResponseEntity
                .ok()
                .build();
    }
}