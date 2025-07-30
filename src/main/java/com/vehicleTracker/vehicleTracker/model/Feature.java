package com.vehicleTracker.vehicleTracker.model;

import com.mongodb.client.model.geojson.Geometry;
import com.vehicleTracker.vehicleTracker.enums.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feature {
    @Id
    private String featureTypeId;

    private FeatureType type;

    private Geometry geometry;

    private boolean isDeleted = false;
}
