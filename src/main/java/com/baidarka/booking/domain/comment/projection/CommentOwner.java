package com.baidarka.booking.domain.comment.projection;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.infrastructure.config.Spring;
import com.baidarka.booking.interfaces.mapper.UserRepresentationToAdvertisementOwner;
import com.baidarka.booking.interfaces.mapper.UserRepresentationToCommentOwnerMapper;
import lombok.Builder;
import lombok.Value;
import org.keycloak.admin.client.resource.UsersResource;

@Value
@Builder
public class CommentOwner {
    String firstName;
    String lastName;
    PrimaryUserProjection primaryUser;

    public static CommentOwner get(PrimaryUserProjection primaryUser) {
        final var userRepresentation =
                Spring.bean(UsersResource.class)
                        .get(primaryUser.getKeycloakUserId().toString()) //todo
                        .toRepresentation();

        return UserRepresentationToCommentOwnerMapper
                .MAPPER.mapFrom(userRepresentation, primaryUser);
    }
}
