package com.baidarka.booking.domain.signup.repository;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PrimaryUserRepository extends Repository<PrimaryUserProjection, Long> {

    @Query(value = """
            SELECT EXISTS(
                        SELECT keycloak_user_id
                                FROM primary_user
                                WHERE keycloak_user_id = :keycloakUserId)
                    """)
    boolean isExistsBy(@Param("keycloakUserId") String keycloakUserId);

    @Modifying
    @Query(value = """
                    INSERT INTO primary_user(keycloak_user_id)
                            VALUES (:keycloakUserId)
                    """)
    void insert(@Param("keycloakUserId") String keycloakUserId);

    @Query(value = """
                    SELECT id FROM primary_user WHERE keycloak_user_id = :keycloakUserId
                    """)
    Long findPrimaryUserIdBy(@Param("keycloakUserId") String keycloakUserId);
}
