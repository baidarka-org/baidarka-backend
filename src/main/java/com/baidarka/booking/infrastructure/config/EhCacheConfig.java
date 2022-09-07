package com.baidarka.booking.infrastructure.config;

import com.baidarka.booking.application.listener.EhCacheListener;
import com.baidarka.booking.interfaces.dto.DownloadPrimaryUserPhotoResponse;
import lombok.RequiredArgsConstructor;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;

import java.time.Duration;

import static javax.cache.Caching.getCachingProvider;
import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;
import static org.ehcache.config.builders.CacheEventListenerConfigurationBuilder.newEventListenerConfiguration;
import static org.ehcache.config.builders.ExpiryPolicyBuilder.timeToIdleExpiration;
import static org.ehcache.config.builders.ResourcePoolsBuilder.newResourcePoolsBuilder;
import static org.ehcache.config.units.MemoryUnit.MB;
import static org.ehcache.event.EventType.*;
import static org.ehcache.jsr107.Eh107Configuration.fromEhcacheCacheConfiguration;

@Configuration
@RequiredArgsConstructor
public class EhCacheConfig {
    public static final String CACHE = "primaryUserPhotoResponseCache";
    private final EhCacheListener listener;

    @Bean
    public CacheManager cacheManager() {
        final var cacheManager = getCachingProvider().getCacheManager();

        javax.cache.configuration.Configuration<String, DownloadPrimaryUserPhotoResponse> config =
                fromEhcacheCacheConfiguration(getCacheConfig());

        cacheManager.createCache(CACHE, config);

        return cacheManager;
    }

    private CacheConfiguration<String, DownloadPrimaryUserPhotoResponse> getCacheConfig() {
        final var pool = newResourcePoolsBuilder()
                .offheap(10, MB)
                .build();

        return newCacheConfigurationBuilder(
                String.class, DownloadPrimaryUserPhotoResponse.class, pool)
                .withExpiry(timeToIdleExpiration(Duration.ofHours(1)))
                .withService(getCacheEventListenerBuilder())
                .build();
    }

    private CacheEventListenerConfigurationBuilder getCacheEventListenerBuilder() {
        return newEventListenerConfiguration(listener, CREATED, EXPIRED, REMOVED)
                .asynchronous()
                .unordered();
    }
}
