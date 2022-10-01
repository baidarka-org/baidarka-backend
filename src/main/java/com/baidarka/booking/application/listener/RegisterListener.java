package com.baidarka.booking.application.listener;

import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterListener {
    public final static String REGISTER_TOPIC = "REGISTER";
    public final static String REGISTER_GROUP = "register-listener";

    private final PrimaryUserService service;

    @KafkaListener(
            topics = REGISTER_TOPIC,
            groupId = REGISTER_GROUP)
    public void on(String keycloakUserId) {
        service.save(keycloakUserId);

        log.info("Received event on {}", REGISTER_GROUP);
    }
}
