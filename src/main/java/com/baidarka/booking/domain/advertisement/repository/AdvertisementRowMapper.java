package com.baidarka.booking.domain.advertisement.repository;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.advertisement.projection.AdvertisementProjection;
import com.baidarka.booking.domain.category.projection.SubCategoryProjection;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AdvertisementRowMapper implements RowMapper<AdvertisementProjection> {
    @Override
    public AdvertisementProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        final var primaryUser =
                PrimaryUserProjection.builder()
                        .id(rs.getLong("primary_user_id"))
                        .keycloakUserId(UUID.fromString(rs.getString("keycloak_user_id")))
                        .build();

        return AdvertisementProjection.builder()
                .id(UUID.fromString(rs.getString("id"))) //todo string
                .name(rs.getString("name"))
                .seat(rs.getInt("seat"))
                .location(rs.getString("location"))
                .pricePerPerson(rs.getBigDecimal("price_per_person"))
                .description(rs.getString("description"))
                .supply(rs.getString("supply"))
                .withDelivery(rs.getBoolean("with_delivery"))
                .isOneDay(rs.getBoolean("is_one_day"))
                .isMultiDay(rs.getBoolean("is_multi_day"))
                .advertisementOwner(
                        AdvertisementOwner.builder()
                                .primaryUser(primaryUser)
                                .build())
                .build();
    }
}
