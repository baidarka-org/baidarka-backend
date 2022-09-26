package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.comment.service.CommentService;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.interfaces.dto.CommentRequest;
import com.baidarka.booking.interfaces.dto.CommentResponse;
import com.baidarka.booking.interfaces.mapper.CommentProjectionToCommentResponseMapper;
import com.baidarka.booking.interfaces.mapper.CommentRequestToCommentProjectionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentOperation {
    private final CommentService commentService;
    private final PrimaryUserService primaryUserService;

    public void save(CommentRequest request, String keycloakUserId) {
        final var primaryUserId = primaryUserService.getPrimaryUserIdBy(keycloakUserId);

        final var comment =
                CommentRequestToCommentProjectionMapper.MAPPER.mapFrom(request, primaryUserId);

        commentService.save(comment);
    }

    public List<CommentResponse> getBy(UUID advertisementId) {
        return commentService.getBy(advertisementId).stream()
                .map(comment ->
                        CommentProjectionToCommentResponseMapper.MAPPER.mapFrom(comment.copy()))
                .toList();
    }
}
