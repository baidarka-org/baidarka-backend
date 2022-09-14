package com.baidarka.booking.domain.signup.service;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;

public interface PrimaryUserService {
    void insert(String keycloakUserId);
    Long getPrimaryUserIdBy(String keycloakUserId);
}
