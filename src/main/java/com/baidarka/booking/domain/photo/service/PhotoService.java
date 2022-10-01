package com.baidarka.booking.domain.photo.service;

import com.baidarka.booking.domain.photo.projection.PhotoProjection;

public interface PhotoService {
    void save(PhotoProjection projection, String id);
    void deletePhotoBy(String key);
}
