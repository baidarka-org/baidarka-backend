package com.baidarka.booking.domain.comment.service;

import com.baidarka.booking.domain.comment.projection.CommentProjection;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    void save(CommentProjection comment);
    List<CommentProjection> getBy(UUID advertisementId);
}
