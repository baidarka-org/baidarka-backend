package com.baidarka.booking.domain.order.controller;

import com.baidarka.booking.application.operation.OrderOperation;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderOperation operation;

    @PostMapping
    public ResponseEntity<Void> order(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                      @RequestBody OrderRequest request) {
        operation.order(request, principal.getName());

        return ResponseEntity
                .ok()
                .build();
    }
}
