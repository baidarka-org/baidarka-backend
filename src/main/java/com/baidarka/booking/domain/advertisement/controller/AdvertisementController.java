package com.baidarka.booking.domain.advertisement.controller;

import com.baidarka.booking.application.operation.AdvertisementOperation;
import com.baidarka.booking.interfaces.dto.AdvertisementBySubCategoryResponse;
import com.baidarka.booking.interfaces.dto.AdvertisementRequest;
import com.baidarka.booking.interfaces.dto.AdvertisementResponse;
import com.baidarka.booking.interfaces.dto.AdvertisementsBySubCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.utility.RoleExpression.AUTHENTICATED;
import static com.baidarka.booking.infrastructure.utility.RoleExpression.REPRESENTATIVE;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementOperation operation;

    @PostMapping
    @PreAuthorize(REPRESENTATIVE)
    public ResponseEntity<AdvertisementResponse> create(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                                        @RequestBody AdvertisementRequest request) {
        return ResponseEntity
                .ok()
                .body(operation.create(request, principal.getName()));
    }

    @GetMapping("{subCategoryId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<List<AdvertisementsBySubCategoryResponse>> getBy(@PathVariable Long subCategoryId) {
        return ResponseEntity
                .ok()
                .body(operation.getBy(subCategoryId));
    }

    @GetMapping("{subCategoryId}/{advertisementId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<AdvertisementBySubCategoryResponse> getBy(@PathVariable Long subCategoryId,
                                                                    @PathVariable UUID advertisementId) {
        return ResponseEntity
                .ok()
                .body(operation.getBy(subCategoryId, advertisementId));
    }
}
