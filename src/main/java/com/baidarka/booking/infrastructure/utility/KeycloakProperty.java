package com.baidarka.booking.infrastructure.utility;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Getter
@Setter
@Primary
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperty {
    String realm;
    String authServerUrl;
    String adminCli = "admin-cli";
    String username = "admin";
    String password = "26013";
}
