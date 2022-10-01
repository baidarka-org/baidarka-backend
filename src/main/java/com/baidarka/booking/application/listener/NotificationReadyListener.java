package com.baidarka.booking.application.listener;

import com.baidarka.booking.domain.notification.projection.NotificationProjection;
import com.baidarka.booking.domain.notification.service.NotificationService;
import com.baidarka.booking.infrastructure.event.NotificationReadyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static com.baidarka.booking.domain.notification.projection.NotificationProjection.builder;

@Component
@RequiredArgsConstructor
public class NotificationReadyListener {
    private final NotificationService service;

    @EventListener(NotificationReadyEvent.class)
    public void on(NotificationReadyEvent event) {
        final var attributes =
                IntStream.range(0, event.getArgs().length)
                        .boxed()
                        .collect(toMap(identity(), index -> event.getArgs()[index]));

        final var notification =
                builder()
                        .notificationType(event.getNotificationType())
                        .primaryUser(event.getPrimaryUser())
                        .attributes(attributes)
                        .build();

        service.save(notification);
    }
}
