package com.vehicleTracker.vehicleTracker.Repository;

import com.vehicleTracker.vehicleTracker.DTO.FeatureResponseDTO;
import com.vehicleTracker.vehicleTracker.model.Feature;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends MongoRepository<Feature, String> {

    @Aggregation(pipeline = {
            "{ $project: { featureTypeId: '$_id', type: 1, geometry: 1 } }"
    })
    List<FeatureResponseDTO> getAllProjectedFeatures();

}
