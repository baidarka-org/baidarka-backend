package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.comment.projection.CommentProjection;
import com.baidarka.booking.interfaces.dto.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentToCommentResponseMapper {
    CommentToCommentResponseMapper MAPPER =
            Mappers.getMapper(CommentToCommentResponseMapper.class);

    CommentResponse mapFrom(CommentProjection comment);
}
