package com.baidarka.booking.domain.order.repository;

import com.baidarka.booking.domain.order.projection.AdvertisementOrderProjection;
import com.baidarka.booking.infrastructure.model.OrderStatus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AdvertisementOrderRepository extends Repository<AdvertisementOrderProjection, Long> {

    @Modifying
    @Query(value = """
                    INSERT INTO advertisement_order
                            (order_status, seat,
                            arrival, departure,
                            primary_user_id, advertisement_id)
                                    VALUES (CAST(:orderStatus AS VARCHAR), :seat,
                                            :arrival, :departure,
                                            :primaryUserId, :advertisementId)
                    """)
    void insert(@Param("orderStatus") OrderStatus orderStatus,
                @Param("seat") Integer seat,
                @Param("arrival") LocalDateTime arrival,
                @Param("departure") LocalDateTime departure,
                @Param("primaryUserId") Long primaryUserId,
                @Param("advertisementId") UUID advertisementId);

    @Query(value = """
                    WITH advertisement_seats AS (SELECT seat FROM advertisement
                                                 WHERE id = :advertisementId),
                        occupied_seats AS (SELECT seat FROM advertisement_order
                                                   WHERE advertisement_id = :advertisementId
                                                     AND (date_trunc('day', arrival) =  (date_trunc('day', :date))
                                                              OR (date_trunc('day', departure) = (date_trunc('day', :date))))),
                        is_exists AS (SELECT EXISTS(SELECT seat FROM occupied_seats) AS exists)
                    SELECT CASE WHEN (SELECT exists FROM is_exists) IS TRUE
                        THEN (SELECT seat FROM advertisement_seats) - (SELECT seat FROM occupied_seats)
                        ELSE (SELECT seat FROM advertisement_seats) END;
                    """)
    Integer findFreeSeatBy(@Param("date") LocalDateTime date,
                           @Param("advertisementId") UUID advertisementId);
}
