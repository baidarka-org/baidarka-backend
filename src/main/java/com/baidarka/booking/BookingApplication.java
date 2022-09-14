package com.baidarka.booking;

import com.baidarka.booking.infrastructure.utility.KeycloakProperty;
import com.baidarka.booking.infrastructure.utility.S3Property;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({S3Property.class, KeycloakProperty.class})
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

}
