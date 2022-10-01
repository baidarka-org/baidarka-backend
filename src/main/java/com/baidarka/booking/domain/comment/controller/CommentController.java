package com.baidarka.booking.domain.comment.controller;

import com.baidarka.booking.application.operation.CommentOperation;
import com.baidarka.booking.interfaces.dto.CommentRequest;
import com.baidarka.booking.interfaces.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.utility.RoleExpression.AUTHENTICATED;
import static com.baidarka.booking.infrastructure.utility.RoleExpression.CLIENT;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentOperation operation;

    @PostMapping
    @PreAuthorize(CLIENT)
    public ResponseEntity<Void> comment(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                        @RequestBody CommentRequest request) {
        final var keycloakUserId = principal.getName();

        operation.save(request, keycloakUserId);

        return ok().build();
    }

    @GetMapping("{advertisementId}")
    @PreAuthorize(AUTHENTICATED)
    public ResponseEntity<List<CommentResponse>> get(@PathVariable UUID advertisementId) {
        return ok().body(operation.getBy(advertisementId));
    }
}
