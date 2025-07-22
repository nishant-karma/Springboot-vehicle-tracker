package com.vehicleTracker.vehicleTracker.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class VehicleLocationDTO {
    private String vehicleId;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;
}
