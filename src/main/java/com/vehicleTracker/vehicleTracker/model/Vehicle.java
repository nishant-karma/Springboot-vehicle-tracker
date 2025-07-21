package com.vehicleTracker.vehicleTracker.model;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@RequiredArgsConstructor
public class Vehicle {

    @Id
    private String vehicleId;
    private String vehicleNumber;
    private String type;
    private String driverName;
    private boolean isActive = true;

}
