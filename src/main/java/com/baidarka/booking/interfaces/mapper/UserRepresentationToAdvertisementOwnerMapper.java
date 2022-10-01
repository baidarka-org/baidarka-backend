package com.baidarka.booking.interfaces.mapper;

import com.baidarka.booking.domain.advertisement.projection.AdvertisementOwner;
import com.baidarka.booking.domain.signup.projection.PrimaryUserProjection;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRepresentationToAdvertisementOwnerMapper {
    UserRepresentationToAdvertisementOwnerMapper MAPPER =
            Mappers.getMapper(UserRepresentationToAdvertisementOwnerMapper.class);

    @Mapping(target = "phoneNumber",
            expression = "java(getPhoneNumber(userRepresentation.getAttributes()))")
    AdvertisementOwner mapFrom(UserRepresentation userRepresentation, PrimaryUserProjection primaryUser);

    default String getPhoneNumber(Map<String, List<String>> attributes) {
        return attributes.get("phoneNumber").get(0);
    }
}
