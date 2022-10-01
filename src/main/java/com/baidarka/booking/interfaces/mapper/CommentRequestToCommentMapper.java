package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.comment.projection.CommentProjection;
import com.baidarka.booking.interfaces.dto.CommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentRequestToCommentMapper {
    CommentRequestToCommentMapper MAPPER =
            Mappers.getMapper(CommentRequestToCommentMapper.class);

    @Mapping(target = "commentOwner.primaryUser.id", source = "primaryUserId")
    CommentProjection mapFrom(CommentRequest request, Long primaryUserId);
}
