package com.baidarka.booking.domain.comment.projection;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class CommentProjection {
    UUID id;
    String review;
    Integer rating;
    CommentOwner commentOwner;
    LocalDateTime uploadedAt;

    public CommentProjection copy() {
        return CommentProjection.builder()
                .id(id)
                .review(review)
                .rating(rating)
                .uploadedAt(uploadedAt)
                .commentOwner(
                        CommentOwner
                                .get(commentOwner
                                        .getPrimaryUser()))
                .build();
    }
}
