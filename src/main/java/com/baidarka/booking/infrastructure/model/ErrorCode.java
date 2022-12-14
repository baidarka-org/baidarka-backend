package com.baidarka.booking.infrastructure.model;

import com.baidarka.booking.infrastructure.exception.*;
import lombok.Getter;

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
    },
    UNKNOWN {
        @Override
        public BaseRuntimeException get(String message) {
            return new UnknownException(message);
        }
    },
    DATA_ACCESS_FORBIDDEN {
        @Override
        public BaseRuntimeException get(String message) {
            return new DataAccessForbiddenException(message);
        }
    }, BUSINESS {
        @Override
        public BaseRuntimeException get(String message) {
            return new BusinessException(message);
        }
    };

    public abstract BaseRuntimeException get(String message);
}
