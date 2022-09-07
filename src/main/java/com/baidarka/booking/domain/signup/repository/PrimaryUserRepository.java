package com.baidarka.booking.domain.signup.repository;

import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PrimaryUserRepository extends Repository<PrimaryUserProjection, Long> {

    @Query(value = """
            SELECT EXISTS(
                        SELECT keycloak_user_id
                                FROM primary_user
                                WHERE keycloak_user_id = :keycloakUserId)
                    """)
    boolean isExists(@Param("keycloakUserId") String keycloakUserId);

    @Modifying
    @Query(value = """
                    INSERT INTO primary_user(keycloak_user_id)
                            VALUES (:keycloakUserId)
                    """)
    void insert(@Param("keycloakUserId") String keycloakUserId);
}
