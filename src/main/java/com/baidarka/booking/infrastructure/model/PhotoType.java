package com.baidarka.booking.infrastructure.model;

import com.baidarka.booking.application.service.AdvertisementPhotoServiceImpl;
import com.baidarka.booking.application.service.PrimaryUserPhotoServiceImpl;
import com.baidarka.booking.domain.photo.service.PhotoService;

import static com.baidarka.booking.infrastructure.config.Spring.bean;

public enum PhotoType {
    ADVERTISEMENT {
        @Override
        public PhotoService get() {
            return bean(AdvertisementPhotoServiceImpl.class);
        }
    },
    PRIMARY_USER {
        @Override
        public PhotoService get() {
            return bean(PrimaryUserPhotoServiceImpl.class);
        }
    };

    public abstract PhotoService get();
}
