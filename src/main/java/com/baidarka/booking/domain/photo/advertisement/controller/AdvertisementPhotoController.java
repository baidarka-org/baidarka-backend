package com.baidarka.booking.domain.photo.advertisement.controller;

import com.amazonaws.HttpMethod;
import com.baidarka.booking.application.operation.PhotoOperation;
import com.baidarka.booking.interfaces.dto.AdvertisementDeleteRequest;
import com.baidarka.booking.interfaces.dto.AdvertisementPhotoRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadResponse;
import com.baidarka.booking.interfaces.dto.PhotoDownloadResponse;
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

import static com.baidarka.booking.infrastructure.utility.RoleExpression.AUTHENTICATED;
import static org.springframework.http.ResponseEntity.ok;
import static com.amazonaws.HttpMethod.valueOf;

@RestController
@RequestMapping("api/v1/photo/{advertisementId}")
@RequiredArgsConstructor
public class AdvertisementPhotoController {
    private final PhotoOperation<AdvertisementPhotoRequest, List<PhotoDownloadResponse>, AdvertisementDeleteRequest> operation;

    @PutMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> upload(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                       @PathVariable String advertisementId,
                                       @RequestParam List<MultipartFile> photos) {
        final var request =
                AdvertisementPhotoRequest.builder()
                        .keycloakUserId(principal.getName())
                        .advertisementId(advertisementId)
                        .photos(photos)
                        .build();

        operation.upload(request);

        return ok().build();
    }

    @GetMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<List<PhotoDownloadResponse>> download(@PathVariable String advertisementId,
                                                                HttpServletRequest servlet) {
        final var request =
                PhotoDownloadRequest.builder()
                        .id(advertisementId)
                        .method(valueOf(servlet.getMethod()))
                        .build();

        return ok().body(operation.download(request));
    }

    @DeleteMapping("{photoId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> delete(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                       @PathVariable UUID advertisementId,
                                       @PathVariable String photoId) {
        final var request =
                AdvertisementDeleteRequest.builder()
                        .advertisementId(advertisementId)
                        .photoId(photoId)
                        .keycloakUserId(principal.getName())
                        .build();

        operation.delete(request);

        return ok().build();
    }
}
