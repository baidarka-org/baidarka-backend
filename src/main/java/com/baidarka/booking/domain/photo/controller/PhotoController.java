package com.baidarka.booking.domain.photo.controller;

import com.amazonaws.HttpMethod;
import com.baidarka.booking.application.operation.PhotoOperation;
import com.baidarka.booking.interfaces.dto.DownloadPrimaryUserPhotoRequest;
import com.baidarka.booking.interfaces.dto.DownloadPrimaryUserPhotoResponse;
import com.baidarka.booking.interfaces.dto.UploadPrimaryUserPhotoRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
        UploadPrimaryUserPhotoRequest.builder()
                .keycloakUserId(principal.getName())
                .photo(photo)
                .build();

        operation.upload(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> delete(@AuthenticationPrincipal KeycloakPrincipal<?> principal) {
        operation.delete(principal.getName());

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<DownloadPrimaryUserPhotoResponse> download(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                                                     HttpServletRequest servlet) {
        final var request =
                DownloadPrimaryUserPhotoRequest.builder()
                        .keycloakUserId(principal.getName())
                        .method(HttpMethod.valueOf(servlet.getMethod()))
                        .build();

        return ResponseEntity
                .ok()
                .body(operation.download(request));
    }
}
