package com.baidarka.booking.domain.signup.service;

public interface PrimaryUserService {
    void insert(String keycloakUserId);
    Long getPrimaryUserIdBy(String keycloakUserId);
}
