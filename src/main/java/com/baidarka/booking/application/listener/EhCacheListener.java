package com.baidarka.booking.application.listener;

import com.baidarka.booking.interfaces.dto.DownloadPrimaryUserPhotoResponse;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EhCacheListener implements CacheEventListener<String, DownloadPrimaryUserPhotoResponse> {
    @Override
    public void onEvent(CacheEvent<? extends String, ? extends DownloadPrimaryUserPhotoResponse> cacheEvent) {
        final var photoURL = cacheEvent.getNewValue().getPhotoURL();

        switch (cacheEvent.getType()) {
            case CREATED -> log.debug("{} been saved to cache",
                    photoURL);
            case REMOVED -> log.debug("{} been removed from cache",
                    photoURL);
            case EXPIRED -> log.debug("{} been has expired",
                    photoURL);
        }
    }
}
