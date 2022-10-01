package com.baidarka.booking.infrastructure.config;

import com.baidarka.booking.infrastructure.utility.KeycloakProperty;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import static org.keycloak.admin.client.KeycloakBuilder.builder;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
    private final KeycloakProperty property;

    @Bean
    public Keycloak keycloak() {
        return builder()
                .serverUrl(property.getAuthServerUrl())
                .realm(property.getRealm())
                .clientId(property.getAdminCli())
                .username(property.getUsername())
                .password(property.getPassword())
                .resteasyClient(getResteasyClient())
                .build();
    }

    @Bean
    @DependsOn("keycloak")
    public UsersResource userResource(Keycloak keycloak) {
        return keycloak
                .realm(property.getRealm())
                .users();
    }
    private ResteasyClient getResteasyClient() {
        return new ResteasyClientBuilder()
                .connectionPoolSize(10)
                .build();
    }
}
