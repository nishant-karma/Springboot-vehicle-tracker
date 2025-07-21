package com.vehicleTracker.vehicleTracker.model;

import com.mongodb.client.model.geojson.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LocationUpdate {
    @Id
    private String locationUpdateId;
    private String vehicleId;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;

    private LocalDateTime timestamp;
}
