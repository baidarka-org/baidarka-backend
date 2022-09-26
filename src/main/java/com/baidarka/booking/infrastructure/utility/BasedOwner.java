package com.baidarka.booking.infrastructure.utility;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.config.Spring;
import org.keycloak.admin.client.resource.UsersResource;

public abstract class BasedOwner<T> {
    protected T get(PrimaryUserProjection primaryUser) {
        final var userRepresentation =
                Spring.bean(UsersResource.class)
                        .get(primaryUser.getKeycloakUserId().toString()) //todo
                        .toRepresentation();

        return null;
    }
}
