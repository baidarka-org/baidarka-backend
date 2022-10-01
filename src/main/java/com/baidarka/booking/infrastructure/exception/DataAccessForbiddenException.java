package com.baidarka.booking.infrastructure.exception;

public class DataAccessForbiddenException extends SecurityException {
    public DataAccessForbiddenException(String message) {
        super(message);
    }
}
