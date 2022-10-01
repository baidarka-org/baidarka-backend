package com.baidarka.booking.domain.signup.service;

public interface PrimaryUserService {
    void save(String keycloakUserId);
    Long getPrimaryUserIdBy(String keycloakUserId);
}
