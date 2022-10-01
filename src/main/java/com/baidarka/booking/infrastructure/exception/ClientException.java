package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorType;

import static com.baidarka.booking.infrastructure.model.ErrorType.CLIENT;

public class ClientException extends BaseRuntimeException {
    public ClientException(String message) {
        super(message);
    }

    @Override
    public ErrorType getErrorType() {
        return CLIENT;
    }
}
