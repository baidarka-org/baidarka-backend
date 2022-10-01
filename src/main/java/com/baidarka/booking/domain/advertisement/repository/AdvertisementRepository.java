package com.baidarka.booking.domain.advertisement.repository;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AdvertisementRepository extends Repository<AdvertisementProjection, UUID> {

    @Query(value = """
                    SELECT EXISTS(
                                SELECT name FROM advertisement
                                            WHERE name = :name
                    )
                    """)
    boolean isExistsBy(@Param("name") String name);

    @Query(value = """
                    SELECT EXISTS(
                                SELECT id FROM advertisement
                                            WHERE id = :id AND is_activated = true
                    )
                    """)
    boolean isExistsBy(@Param("id") UUID id);

    @Query(value = """
                    INSERT INTO advertisement(
                        name, location, seat,
                        price_per_person, description,
                        supply, with_delivery,
                        is_one_day, is_multi_day,
                        sub_category_id, primary_user_id)
                                    VALUES (:name, :location, :seat,
                                            :pricePerPerson, :description,
                                            :supply, :withDelivery,
                                            :isOneDay, :isMultiDay,
                                            :subCategoryId, :primaryUserId) RETURNING id
                    """)
    UUID insert(@Param("name") String name,
                @Param("location") String location,
                @Param("seat") Integer seat,
                @Param("pricePerPerson") BigDecimal pricePerPerson,
                @Param("description") String description,
                @Param("supply") String supply,
                @Param("withDelivery") boolean withDelivery,
                @Param("isOneDay") boolean isOneDay,
                @Param("isMultiDay") boolean isMultiDay,
                @Param("subCategoryId") Long subCategoryId,
                @Param("primaryUserId") Long primaryUserId);

    @Query(value = """
                    SELECT
                        a.id, a.name, a.location,
                        a.seat, a.price_per_person, a.description,
                        a.supply, a.with_delivery, a.is_one_day,
                        a.is_multi_day, a.sub_category_id,
                        a.primary_user_id, pu.keycloak_user_id
                            FROM advertisement a
                            JOIN primary_user pu on a.primary_user_id = pu.id
                            WHERE sub_category_id = :subCategoryId
                                AND is_activated = true
                            ORDER BY a.uploaded_at
                    """,
            rowMapperClass = AdvertisementRowMapper.class)
    List<AdvertisementProjection> findAdvertisementsBy(@Param("subCategoryId") Long subCategoryId);

    @Query(value = """
                    SELECT
                        a.id, a.name, a.location,
                        a.seat, a.price_per_person, a.description,
                        a.supply, a.with_delivery, a.is_one_day,
                        a.is_multi_day, a.sub_category_id,
                        a.primary_user_id, pu.keycloak_user_id
                            FROM advertisement a
                            JOIN primary_user pu on a.primary_user_id = pu.id
                            WHERE sub_category_id = :subCategoryId
                                AND a.id = :advertisementId
                                AND is_activated = true
                    """,
            rowMapperClass = AdvertisementRowMapper.class)
    AdvertisementProjection findAdvertisementBy(@Param("subCategoryId") Long subCategoryId,
                                                @Param("advertisementId") UUID advertisementId);

    @Query(value = """
                    SELECT name FROM advertisement WHERE id = :advertisementId
                    """)
    String findAdvertisementNameBy(@Param("advertisementId") UUID advertisementId);

    @Query(value = """
                    SELECT EXISTS (
                                    SELECT id FROM advertisement
                                                WHERE primary_user_id = :primaryUserId
                                                AND id = :advertisementId)
                    """)
    boolean isOwnerBy(@Param("primaryUserId") Long primaryUserId,
                      @Param("advertisementId") UUID advertisementId);
}
