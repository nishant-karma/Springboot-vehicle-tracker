package com.vehicleTracker.vehicleTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VehicleTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleTrackerApplication.class, args);
	}

}
