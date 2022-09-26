package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import static com.baidarka.booking.infrastructure.model.ErrorType.BUSINESS;

public class UnknownException extends BaseRuntimeException {
    public UnknownException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return BUSINESS;
    }
}
