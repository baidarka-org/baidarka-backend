package com.baidarka.booking.domain.order.controller;

import com.baidarka.booking.application.operation.OrderOperation;
import com.baidarka.booking.interfaces.dto.FreeSeatsRequest;
import com.baidarka.booking.interfaces.dto.FreeSeatsResponse;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.baidarka.booking.infrastructure.utility.RoleExpression.CLIENT;
import static com.baidarka.booking.infrastructure.utility.RoleExpression.PERMIT_ALL;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderOperation operation;

    @PostMapping
    @PreAuthorize(CLIENT)
    public ResponseEntity<Void> order(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                      @RequestBody OrderRequest request) {
        operation.order(request, principal.getName());

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("seat")
    @PreAuthorize(PERMIT_ALL)
    public ResponseEntity<FreeSeatsResponse> getFreeSeats(@RequestBody FreeSeatsRequest request) {
        return ResponseEntity
                .ok()
                .body(operation.getFreeSeats(request));
    }
}
