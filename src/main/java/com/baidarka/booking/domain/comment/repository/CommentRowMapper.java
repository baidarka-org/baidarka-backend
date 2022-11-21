package com.baidarka.booking.domain.comment.repository;

import com.baidarka.booking.domain.comment.projection.CommentOwner;
import com.baidarka.booking.domain.comment.projection.CommentProjection;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static com.baidarka.booking.infrastructure.utility.DateConverter.convertToLocalDateTimeViaInstant;

public class CommentRowMapper implements RowMapper<CommentProjection> {
    @Override
    public CommentProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        final var primaryUser =
                PrimaryUserProjection.builder()
                        .id(rs.getLong("id"))
                        .keycloakUserId(UUID.fromString(rs.getString("keycloak_user_id")))
                        .build();

        final var commentOwner =
                CommentOwner.builder()
                        .primaryUser(primaryUser)
                        .build();
        return CommentProjection.builder()
                .review(rs.getString("review"))
                .rating(rs.getInt("rating"))
                .uploadedAt(convertToLocalDateTimeViaInstant(rs.getDate("uploaded_at")))
                .commentOwner(commentOwner)
                .build();
    }
}
