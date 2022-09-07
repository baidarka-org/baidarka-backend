package com.baidarka.booking.infrastructure.model;

import com.baidarka.booking.infrastructure.exception.*;

public enum ErrorCode {
    DATA_IS_NOT_VALID {
        @Override
        public BaseRuntimeException get(String message) {
            return new DataIsNotValidException(message);
        }
    },
    DATA_IS_NOT_FOUND {
        @Override
        public BaseRuntimeException get(String message) {
            return new DataIsNotFoundException(message);
        }
    },
    MEDIA_OPERATION_FAILED {
        @Override
        public BaseRuntimeException get(String message) {
            return new MediaOperationException(message);
        }
    },
    DATA_ACCESS_DENIED {
        @Override
        public BaseRuntimeException get(String message) {
            return new DataAccessException(message);
        }
    };

    public abstract BaseRuntimeException get(String message);
}
