package com.baidarka.booking.infrastructure.utility;

import com.baidarka.booking.interfaces.factory.PropertySourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Primary //todo ???????
@Configuration
@PropertySource(
        value = "classpath:application-dev.yaml",
        factory = PropertySourceFactory.class)
@ConfigurationProperties(prefix = "aws.s3")
public class S3Property {
    private String accessKeyId;
    private String clientId;
    private String region;
    private String bucketName;
    private String defaultProfileImage;
}
