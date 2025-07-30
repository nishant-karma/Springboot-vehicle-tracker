package com.vehicleTracker.vehicleTracker.DTO;

import com.mongodb.client.model.geojson.Geometry;
import com.vehicleTracker.vehicleTracker.enums.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureResponseDTO {

    @Field("_id")
    private String featureTypeId;

    private FeatureType type;

    private Object geometry;
}
