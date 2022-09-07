package com.baidarka.booking.infrastructure.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleExpression {
    public static final String PERMIT_ALL = "permitAll()";
    public static final String AUTHENTICATED = "isAuthenticated()";
    public static final String CLIENT = "hasAuthority('CLIENT')";
    public static final String REPRESENTATIVE = "hasAuthority('REPRESENTATIVE')";
    public static final String ADMIN = "hasAuthority('ADMIN')";
}
