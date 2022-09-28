package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import static com.baidarka.booking.infrastructure.model.ErrorType.SECURITY;

public class DataAccessForbiddenException extends BaseRuntimeException {
    public DataAccessForbiddenException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return SECURITY;
    }
}
