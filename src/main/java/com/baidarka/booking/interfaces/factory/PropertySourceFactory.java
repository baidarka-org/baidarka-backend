package com.baidarka.booking.interfaces.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class PropertySourceFactory implements org.springframework.core.io.support.PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        final var factory = new YamlPropertiesFactoryBean();

        final var factoryResource = resource.getResource();

        factory.setResources(factoryResource);

        return new PropertiesPropertySource(
                requireNonNull(factoryResource.getFilename()),
                requireNonNull(factory.getObject()));
    }
}

