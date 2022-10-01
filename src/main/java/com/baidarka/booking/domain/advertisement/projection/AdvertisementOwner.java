package com.baidarka.booking.domain.advertisement.projection;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.interfaces.mapper.UserRepresentationToAdvertisementOwnerMapper;
import lombok.Builder;
import lombok.Value;

import static com.baidarka.booking.infrastructure.utility.BasedOwner.getUserRepresentation;

@Value
@Builder
public class AdvertisementOwner {
    String firstName;
    String lastName;
    String phoneNumber;
    PrimaryUserProjection primaryUser;

    public static AdvertisementOwner get(PrimaryUserProjection primaryUser) {
        final var userRepresentation =
                getUserRepresentation(primaryUser.getKeycloakUserId().toString());

        return UserRepresentationToAdvertisementOwnerMapper
                .MAPPER.mapFrom(userRepresentation, primaryUser);
    }
}
