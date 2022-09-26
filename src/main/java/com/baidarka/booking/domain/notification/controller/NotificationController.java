package com.baidarka.booking.domain.notification.controller;

import com.baidarka.booking.application.operation.NotificationOperation;
import com.baidarka.booking.interfaces.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationOperation operation;

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> get(@AuthenticationPrincipal KeycloakPrincipal<?> principal) {
        final var keycloakUserId = principal.getName();

        return ResponseEntity
                .ok()
                .body(operation.get(keycloakUserId));
    }
}
