package com.baidarka.booking.domain.photo.repository;

import com.baidarka.booking.domain.photo.projection.PhotoProjection;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AdvertisementPhotoRepository extends Repository<PhotoProjection, UUID> {

    @Modifying
    @Query(value = """
                    WITH photo_id AS (INSERT INTO photo (id, keys)
                                                VALUES (:id, :key) RETURNING id)
                        INSERT INTO advertisement_photo (advertisement_id, photo_id)
                                                VALUES (CAST(:advertisementId AS UUID), (SELECT id FROM photo_id)) --todo
                    """)
    void update(@Param("id") UUID id,
                @Param("key") String key,
                @Param("advertisementId") String advertisementId);

    @Query(value = """
                    SELECT p.keys FROM advertisement_photo
                                JOIN photo p ON p.id = advertisement_photo.photo_id
                                WHERE advertisement_id = CAST(:advertisementId AS UUID)
                                ORDER BY p.uploaded_at
                    """)
    List<String> findKeysBy(@Param("advertisementId") String advertisementId);

    @Query(value = """
                    SELECT keys FROM photo WHERE id = CAST(:photoId AS UUID)
                    """)
    String findKeyBy(@Param("photoId") String photoId);

    @Modifying
    @Query(value = """
                    DELETE FROM photo WHERE keys = :key
                    """)
    void delete(@Param("key") String key);
}
