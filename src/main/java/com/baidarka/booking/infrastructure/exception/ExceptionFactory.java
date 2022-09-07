package com.baidarka.booking.infrastructure.exception;

import com.baidarka.booking.infrastructure.model.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class ExceptionFactory {
    public static Factory factory() {
        return new Factory();
    }
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Factory {
        private ErrorCode errorCode;
        private String message;

        public Factory code(ErrorCode errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Factory message(String message) {
            this.message = message;
            return this;
        }

        public BaseRuntimeException get() {
            return errorCode.get(message);
        }
    }
}
