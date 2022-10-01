package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import static com.baidarka.booking.infrastructure.model.ErrorType.BUSINESS;

public class BusinessException extends BaseRuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return BUSINESS;
    }
}
