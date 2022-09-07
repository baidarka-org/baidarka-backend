package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import static com.baidarka.booking.infrastructure.model.ErrorType.VALIDATION;

public class DataIsNotValidException extends BaseRuntimeException {
    public DataIsNotValidException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return VALIDATION;
    }
}
