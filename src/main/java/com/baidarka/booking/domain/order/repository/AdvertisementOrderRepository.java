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
                    WITH all_occupied_seats AS (SELECT seat,
                                                       generate_series(arrival, departure, '1 day'::interval) AS date
                                                FROM advertisement_order
                                                WHERE expires_at > current_timestamp
                                                  AND advertisement_id = :advertisementId),
                         all_advertisement_seats AS (SELECT seat
                                                     FROM advertisement
                                                     WHERE id = :advertisementId),
                         date_occupied_seats AS (SELECT seat
                                                 FROM all_occupied_seats
                                                 WHERE date_trunc('day', date) = date_trunc('day', :date)),
                         is_exists AS (SELECT EXISTS (SELECT seat FROM date_occupied_seats) AS exists)
                    SELECT CASE WHEN (SELECT exists FROM is_exists)
                        THEN (SELECT (SELECT aas.seat FROM all_advertisement_seats aas) - sum(dos.seat) FROM date_occupied_seats dos)
                        ELSE (SELECT aas.seat FROM all_advertisement_seats aas) END;
                    """)
    Integer findFreeSeatsBy(@Param("date") LocalDateTime date,
                            @Param("advertisementId") UUID advertisementId);

    @Query(value = """
                    WITH user_order AS (SELECT arrival, departure
                                        FROM advertisement_order
                                        WHERE advertisement_id = :advertisementId
                                          AND primary_user_id = 1
                                          AND expires_at > current_timestamp LIMIT 1),
                        order_interval AS (SELECT date_trunc('day', gs) AS date_interval
                                                            FROM generate_series(
                                                                            (SELECT arrival FROM user_order),
                                                                            (SELECT departure FROM user_order),
                                                                            '1 day'::interval) AS gs),
                        in_interval AS (SELECT (CASE WHEN
                                                        date_interval = date_trunc('day', :arrival)
                                                            OR date_interval = date_trunc('day', :departure)
                                                    THEN true ELSE false END) AS belongs_to FROM order_interval)
                    SELECT EXISTS (SELECT belongs_to FROM in_interval WHERE belongs_to = true);
                    """)
    boolean isBookedBy(@Param("advertisementId") UUID advertisementId,
                       @Param("primaryUserId") Long primaryUserId,
                       @Param("arrival") LocalDateTime arrival,
                       @Param("departure") LocalDateTime departure);

    @Query(value = """
                    SELECT EXISTS (
                                    SELECT advertisement_id FROM advertisement_order
                                                            WHERE advertisement_id = :advertisementId
                                                            AND primary_user_id = :primaryUserId
                                                            AND order_status = 'ORDERED'
                                                            AND arrival <= current_timestamp)
                    """)
    boolean isOrderedAndPassedBy(@Param("advertisementId") UUID advertisementId,
                                 @Param("primaryUserId") Long primaryUserId);
}
