package com.baidarka.booking.interfaces.factory;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;

import java.util.Objects;

public class PropertySourceFactory implements org.springframework.core.io.support.PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        final var factory = new YamlPropertiesFactoryBean();

        final var factoryResource = resource.getResource();

        factory.setResources(factoryResource);

        return new PropertiesPropertySource(
                Objects.requireNonNull(factoryResource.getFilename()),
                Objects.requireNonNull(factory.getObject()));
    }
}

