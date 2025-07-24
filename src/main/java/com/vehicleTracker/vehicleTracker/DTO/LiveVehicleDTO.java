package com.vehicleTracker.vehicleTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;



public interface LiveVehicleDTO {
    @Value("#{target._id}")
    String getVehicleId();

    String getVehicleNumber();
    Double getLongitude();
    Double getLatitude();
    LocalDateTime getTimestamp();

}
