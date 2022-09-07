package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import java.util.Objects;

public abstract class BaseRuntimeException extends RuntimeException {
    public BaseRuntimeException(String message) {
        super(Objects.requireNonNullElse(message, ""));
    }

    public abstract ErrorType getErrorType();
}
