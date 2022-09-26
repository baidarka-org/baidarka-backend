package com.baidarka.booking.domain.order.controller;

import com.baidarka.booking.application.operation.OrderOperation;
import com.baidarka.booking.interfaces.dto.FreeSeatsByDateRequest;
import com.baidarka.booking.interfaces.dto.FreeSeatsByDateResponse;
import com.baidarka.booking.interfaces.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.baidarka.booking.infrastructure.utility.RoleExpression.AUTHENTICATED;
import static com.baidarka.booking.infrastructure.utility.RoleExpression.CLIENT;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderOperation operation;

    @PostMapping
    @PreAuthorize(AUTHENTICATED) //todo
    public ResponseEntity<Void> order(@AuthenticationPrincipal KeycloakPrincipal<?> principal,
                                      @RequestBody OrderRequest request) {
        operation.order(request, principal.getName());

        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("seat")
    public ResponseEntity<FreeSeatsByDateResponse> getFreeSeats(@RequestBody FreeSeatsByDateRequest request) {
        return ResponseEntity
                .ok()
                .body(operation.getFreeSeats(request));
    }
}
