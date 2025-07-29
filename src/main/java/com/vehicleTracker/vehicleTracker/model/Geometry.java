package com.vehicleTracker.vehicleTracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    @Id
    private String polygonId;

    private com.mongodb.client.model.geojson.Geometry geometry;

    private boolean isDeleted = false;


}
