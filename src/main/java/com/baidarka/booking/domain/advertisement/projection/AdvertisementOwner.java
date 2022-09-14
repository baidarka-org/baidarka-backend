package com.baidarka.booking.domain.advertisement.projection;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.config.Spring;
import com.baidarka.booking.interfaces.mapper.UserRepresentationToAdvertisementOwner;
import lombok.Builder;
import lombok.Value;
import org.keycloak.admin.client.resource.UsersResource;

@Value
@Builder
public class AdvertisementOwner {
    String firstName;
    String lastName;
    String phoneNumber;
    PrimaryUserProjection primaryUser;

    public static AdvertisementOwner get(PrimaryUserProjection primaryUser) {
        final var userRepresentation =
                Spring.bean(UsersResource.class)
                        .get(primaryUser.getKeycloakUserId().toString()) //todo
                        .toRepresentation();

        return UserRepresentationToAdvertisementOwner
                .MAPPER.mapFrom(userRepresentation, primaryUser);
    }
}
