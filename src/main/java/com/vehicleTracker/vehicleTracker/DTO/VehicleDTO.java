package com.vehicleTracker.vehicleTracker.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VehicleDTO {

    private String vehicleId;
    private String vehicleNumber;
    private String type;
    private String driverName;
    private boolean isActive;


}
