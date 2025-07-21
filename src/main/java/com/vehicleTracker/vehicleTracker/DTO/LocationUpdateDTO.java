package com.vehicleTracker.vehicleTracker.DTO;

import com.mongodb.client.model.geojson.Point;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor

public class LocationUpdateDTO {
    private String locationUpdateId;
    private String vehicleId;
    private Point location;
    private LocalDateTime timestamp;


}
