package com.baidarka.booking.domain.photo.service;

import com.baidarka.booking.domain.photo.projection.PrimaryUserPhotoProjection;

public interface PrimaryUserPhotoService {
    void save(PrimaryUserPhotoProjection projection, String keycloakUserId);
    void deleteBy(String key);
}
