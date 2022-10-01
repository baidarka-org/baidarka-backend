package com.baidarka.booking.domain.photo.primaryuser.controller;

import com.amazonaws.HttpMethod;
import com.baidarka.booking.application.operation.PhotoOperation;
import com.baidarka.booking.interfaces.dto.PhotoDownloadRequest;
import com.baidarka.booking.interfaces.dto.PhotoDownloadResponse;
import com.baidarka.booking.interfaces.dto.PhotoRequest;
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
public class PrimaryUserPhotoController {
    private final PhotoOperation<PhotoRequest, PhotoDownloadResponse, String> operation;

    @PutMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> upload(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                       @RequestParam MultipartFile photo) {
        final var request =
                PhotoRequest.builder()
                        .keycloakUserId(principal.getName())
                        .photo(photo)
                        .build();

        operation.upload(request);

        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<PhotoDownloadResponse> download(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                                          HttpServletRequest servlet) {
        final var request =
                PhotoDownloadRequest.builder()
                        .id(principal.getName())
                        .method(HttpMethod.valueOf(servlet.getMethod()))
                        .build();

        return ResponseEntity
                .ok()
                .body(operation.download(request));
    }

    @DeleteMapping
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<Void> delete(@AuthenticationPrincipal KeycloakPrincipal<?> principal) {
        operation.delete(principal.getName());

        return ResponseEntity
                .ok()
                .build();
    }
}
