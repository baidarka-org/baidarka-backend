package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import static com.baidarka.booking.infrastructure.model.ErrorType.BUSINESS;

public class DataAccessException extends BaseRuntimeException {
    public DataAccessException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return BUSINESS;
    }
}
