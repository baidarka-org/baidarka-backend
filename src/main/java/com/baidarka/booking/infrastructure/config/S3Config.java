package com.baidarka.booking.infrastructure.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.baidarka.booking.infrastructure.utility.S3Property;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Property awsProperties;

    @Bean
    public AmazonS3 client() {
        final var awsCredentials = new BasicAWSCredentials(
                awsProperties.getAccessKeyId(),
                awsProperties.getClientId());

        return AmazonS3ClientBuilder.standard()
                .withRegion(awsProperties.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    @DependsOn("client")
    public TransferManager transferManager(AmazonS3 client) {
        return TransferManagerBuilder.standard()
                .withS3Client(client)
                .build();
    }
}

