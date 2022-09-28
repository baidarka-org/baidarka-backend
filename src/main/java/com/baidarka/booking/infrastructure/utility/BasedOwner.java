package com.baidarka.booking.infrastructure.utility;

import com.baidarka.booking.infrastructure.config.Spring;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;

public class BasedOwner {
    public static UserRepresentation getUserRepresentation(String keycloakUserId) {
        return Spring.bean(UsersResource.class)
                        .get(keycloakUserId)
                        .toRepresentation();
    }
}
