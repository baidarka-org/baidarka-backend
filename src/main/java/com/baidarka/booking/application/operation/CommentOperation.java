package com.baidarka.booking.application.operation;

import com.baidarka.booking.domain.comment.service.CommentService;
import com.baidarka.booking.domain.order.service.AdvertisementOrderService;
import com.baidarka.booking.domain.signup.service.PrimaryUserService;
import com.baidarka.booking.interfaces.dto.CommentRequest;
import com.baidarka.booking.interfaces.dto.CommentResponse;
import com.baidarka.booking.interfaces.mapper.CommentRequestToCommentMapper;
import com.baidarka.booking.interfaces.mapper.CommentToCommentResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.DATA_ACCESS_FORBIDDEN;

@Component
@RequiredArgsConstructor
public class CommentOperation {
    private final CommentService commentService;
    private final PrimaryUserService primaryUserService;
    private final AdvertisementOrderService advertisementOrderService;

    public void save(CommentRequest request, String keycloakUserId) {
        final var primaryUserId = primaryUserService.getPrimaryUserIdBy(keycloakUserId);

        final var advertisementId = request.getAdvertisementId();

        if (commentService.isAlreadyCommented(advertisementId, primaryUserId)) {
            throw factory()
                    .message("You have already leaved a comment")
                    .code(DATA_ACCESS_FORBIDDEN)
                    .get();
        }

        if (!advertisementOrderService.isAlreadyOrderedAndPassedBy(advertisementId, primaryUserId)) {
            throw factory()
                    .message("""
                            You have not arrive yet or ordered the
                            advertisement to leave a comment
                            """)
                    .code(DATA_ACCESS_FORBIDDEN)
                    .get();
        }

        final var comment =
                CommentRequestToCommentMapper.MAPPER.mapFrom(request, primaryUserId);

        commentService.save(comment);
    }

    public List<CommentResponse> getBy(UUID advertisementId) {
        return commentService.getBy(advertisementId).stream()
                .map(comment ->
                        CommentToCommentResponseMapper.MAPPER.mapFrom(comment.copy()))
                .toList();
    }
}
