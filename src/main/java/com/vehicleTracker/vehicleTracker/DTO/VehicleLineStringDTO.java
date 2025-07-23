package com.vehicleTracker.vehicleTracker.DTO;

import java.util.List;

public interface VehicleLineStringDTO {
    String getVehicleId();
    String getVehicleNumber();
    List<List<Double>> getLineString();
}
