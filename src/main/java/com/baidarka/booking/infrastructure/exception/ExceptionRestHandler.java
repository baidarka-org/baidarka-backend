package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

import static java.time.Instant.now;

@RestControllerAdvice
public class ExceptionRestHandler {

    @ExceptionHandler(DataIsNotValidException.class)
    public ResponseEntity<ExceptionResponse> handle(DataIsNotValidException dinve) {
        return ResponseEntity
                .badRequest()
                .body(getResponse(dinve));
    }

    @ExceptionHandler(DataIsNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(DataIsNotFoundException dinfe) {
        return ResponseEntity
                .status(409)
                .body(getResponse(dinfe));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> handle(DataAccessException dae) {
        return ResponseEntity
                .internalServerError()
                .body(getResponse(dae));
    }

    @ExceptionHandler(MediaOperationException.class)
    public ResponseEntity<ExceptionResponse> handle(MediaOperationException moe) {
        return ResponseEntity
                .status(503)
                .body(getResponse(moe));
    }

    @ExceptionHandler(DataAccessForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handle(DataAccessForbiddenException dafe) {
        return ResponseEntity
                .status(403)
                .body(getResponse(dafe));
    }

    private ExceptionResponse getResponse(BaseRuntimeException bre) {
        return ExceptionResponse.builder()
                .exception(bre.getClass().getSimpleName())
                .message(bre.getMessage())
                .type(bre.getErrorType())
                .build();
    }

    @Value
    @Builder
    private static class ExceptionResponse {
        String exception;
        String message;
        ErrorType type;
        Instant timestamp = now();
    }
}
