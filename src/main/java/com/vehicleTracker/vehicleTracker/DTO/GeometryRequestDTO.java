package com.vehicleTracker.vehicleTracker.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

@Data
@AllArgsConstructor
@NoArgsConstructor
    public class GeometryRequestDTO {
        private GeoJsonPolygon coordinates;

    }
