package com.baidarka.booking.domain.comment.repository;

import com.baidarka.booking.domain.comment.projection.CommentProjection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends Repository<CommentProjection, UUID> {

    @Modifying
    @Query(value = """
                    INSERT INTO comment
                                (id, review, rating, primary_user_id)
                                    VALUES (:id, :review, :rating, :primaryUserId)
                    """)
    void insert(@Param("id") UUID id,
                @Param("review") String review,
                @Param("rating") Integer rating,
                @Param("primaryUserId") Long primaryUserId);

    @Modifying
    @Query(value = """
                    INSERT INTO advertisement_comment
                                (advertisement_id, comment_id)
                                    VALUES (:advertisementId, :commentId)
                    """)
    void insert(@Param("advertisementId") UUID advertisementId,
                @Param("commentId") UUID commentId);

    default void insert(CommentProjection comment) {
        final var commentId = UUID.randomUUID();

        insert(commentId, comment.getReview(),
                comment.getRating(),
                comment.getPrimaryUser().getId());

        insert(commentId, comment.getAdvertisementId());
    }

    @Query(value = """
                    SELECT review, rating,
                        (SELECT id, keycloak_user_id FROM primary_user WHERE id = primary_user_id)
                            FROM comment c
                            JOIN advertisement_comment ac ON c = ac.comment_id
                            WHERE ac.advertisement_id = :advertisementId
                    """)
    List<CommentProjection> findByAdvertisementId(@Param("advertisementId") UUID advertisementId);
}
