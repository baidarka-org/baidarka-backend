package com.baidarka.booking.infrastructure.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static com.baidarka.booking.infrastructure.exception.ExceptionFactory.factory;
import static com.baidarka.booking.infrastructure.model.ErrorCode.BUSINESS;

@Component
public class Spring implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        if (context == null) {
            throw factory()
                    .code(BUSINESS)
                    .message("Spring utility class not initialized")
                    .get();
        }

        return context;
    }

    public static <T> T bean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
}
