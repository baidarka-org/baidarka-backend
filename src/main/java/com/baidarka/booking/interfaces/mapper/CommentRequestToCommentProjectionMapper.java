package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.comment.projection.CommentProjection;
import com.baidarka.booking.interfaces.dto.CommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentRequestToCommentProjectionMapper {
    CommentRequestToCommentProjectionMapper MAPPER =
            Mappers.getMapper(CommentRequestToCommentProjectionMapper.class);

    @Mapping(target = "primaryUser.id", source = "primaryUserId")
    CommentProjection mapFrom(CommentRequest request, Long primaryUserId);
}
