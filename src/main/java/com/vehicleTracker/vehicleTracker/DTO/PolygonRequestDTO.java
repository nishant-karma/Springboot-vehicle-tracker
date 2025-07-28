package com.vehicleTracker.vehicleTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolygonRequestDTO {
    private List<List<List<Double>>> coordinates;

}
