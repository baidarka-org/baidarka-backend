package com.baidarka.booking.domain.photo.primaryuser.repository;

import com.baidarka.booking.domain.photo.projection.PhotoProjection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PrimaryUserPhotoRepository extends Repository<PhotoProjection, UUID> {

    @Modifying
    @Query(value = """
                    WITH photo_generated_uuid AS (
                                INSERT INTO primary_user_photo (id, key)
                                    VALUES (:id, :key) RETURNING id --todo
                                    )
                    UPDATE primary_user
                                    SET primary_user_photo_id = (SELECT id FROM photo_generated_uuid)
                                    WHERE keycloak_user_id = :keycloakUserId
                    """)
    void update(@Param("id") UUID id,
                @Param("key") String key,
                @Param("keycloakUserId") String keycloakUserId);

    @Query(value = """
                    SELECT pup.key FROM primary_user pu
                                    JOIN primary_user_photo pup
                                    ON pu.primary_user_photo_id = pup.id
                                    WHERE pu.keycloak_user_id = :keycloakUserId
                    """)
    String findKeyBy(@Param("keycloakUserId") String keycloakUserId);


    @Modifying
    @Query(value = """
                    DELETE FROM primary_user_photo
                                WHERE key = :key
                                    AND is_default = false
                    """)
    void delete(@Param("key") String key);
}
