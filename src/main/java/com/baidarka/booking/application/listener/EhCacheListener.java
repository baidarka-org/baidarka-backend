package com.baidarka.booking.application.listener;

import com.baidarka.booking.interfaces.dto.DownloadPhotoResponse;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EhCacheListener implements CacheEventListener<String, DownloadPhotoResponse> {
    @Override
    public void onEvent(CacheEvent<? extends String, ? extends DownloadPhotoResponse> cacheEvent) {
        final var presignedUrl = cacheEvent.getNewValue().getPresignedUrl();

        switch (cacheEvent.getType()) {
            case CREATED -> log.debug("{} been saved to cache",
                    presignedUrl);
            case REMOVED -> log.debug("{} been removed from cache",
                    presignedUrl);
            case EXPIRED -> log.debug("{} been has expired",
                    presignedUrl);
        }
    }
}
