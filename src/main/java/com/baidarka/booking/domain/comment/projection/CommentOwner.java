package com.baidarka.booking.domain.comment.projection;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import com.baidarka.booking.interfaces.mapper.UserRepresentationToCommentOwnerMapper;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static com.baidarka.booking.infrastructure.utility.BasedOwner.getUserRepresentation;

@Value
@Builder
public class CommentOwner {
    String firstName;
    String lastName;
    PrimaryUserProjection primaryUser;

    public static CommentOwner get(PrimaryUserProjection primaryUser) {
        final var userRepresentation =
                getUserRepresentation(primaryUser.getKeycloakUserId().toString());

        return UserRepresentationToCommentOwnerMapper
                .MAPPER.mapFrom(userRepresentation, primaryUser);
    }
}
