package com.vehicleTracker.vehicleTracker.DTO;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LocationUpdateRequest {
    private double latitude;
    private double longitude;
}
