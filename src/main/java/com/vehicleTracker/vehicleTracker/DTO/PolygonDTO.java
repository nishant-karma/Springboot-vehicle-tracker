package com.vehicleTracker.vehicleTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolygonDTO {

    private String polygonId;

    private String type;

    private List<List<List<Double>>> coordinates;
}
