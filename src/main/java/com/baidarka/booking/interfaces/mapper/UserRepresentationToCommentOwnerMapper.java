package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.comment.projection.CommentOwner;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRepresentationToCommentOwnerMapper {
    UserRepresentationToCommentOwnerMapper MAPPER =
            Mappers.getMapper(UserRepresentationToCommentOwnerMapper.class);
    CommentOwner mapFrom(UserRepresentation userRepresentation, PrimaryUserProjection primaryUser);
}
