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
    void insertComment(@Param("id") UUID id,
                @Param("review") String review,
                @Param("rating") Integer rating,
                @Param("primaryUserId") Long primaryUserId);

    @Modifying
    @Query(value = """
                    INSERT INTO advertisement_comment
                                (advertisement_id, comment_id)
                                    VALUES (:advertisementId, :commentId)
                    """)
    void insertCommentIntoAdvertisement(@Param("advertisementId") UUID advertisementId,
                @Param("commentId") UUID commentId);

    @Query(value = """
                    SELECT c.review, c.rating, c.uploaded_at, 
                            pu.id, pu.keycloak_user_id
                            FROM comment c
                            JOIN advertisement_comment ac ON c.id = ac.comment_id
                            JOIN primary_user pu ON c.primary_user_id = pu.id
                            WHERE ac.advertisement_id = :advertisementId
                    """,
            rowMapperClass = CommentRowMapper.class)
    List<CommentProjection> findByAdvertisementId(@Param("advertisementId") UUID advertisementId);

    @Query(value = """
                    SELECT EXISTS (SELECT primary_user_id FROM advertisement_comment ac 
                                                            JOIN comment c ON c.id = ac.comment_id
                                                            WHERE ac.advertisement_id = :advertisementId
                                                            AND c.primary_user_id = :primaryUserId)
                    """)
    boolean isAlreadyCommented(@Param("advertisementId") UUID advertisementId,
                               @Param("primaryUserId") Long primaryUserId);
}
