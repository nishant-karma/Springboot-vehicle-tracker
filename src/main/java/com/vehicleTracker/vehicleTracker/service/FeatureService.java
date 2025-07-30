package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.FeatureResponseDTO;
import com.vehicleTracker.vehicleTracker.DTO.FeatureRequestDTO;
import com.vehicleTracker.vehicleTracker.Repository.FeatureRepository;
import com.vehicleTracker.vehicleTracker.exceptions.PolygonNotFoundException;
import com.vehicleTracker.vehicleTracker.model.Feature;
import com.mongodb.client.model.geojson.Geometry;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository){
        this.featureRepository = featureRepository;
    }

    // Save new feature (polygon or other geometry)
    public FeatureResponseDTO saveFeature(FeatureRequestDTO featureRequestDTO){
        Geometry geometry = featureRequestDTO.getGeometry();
        if (geometry == null) {
            throw new IllegalArgumentException("Geometry cannot be null");
        }

        Feature feature = new Feature();
        feature.setGeometry(geometry);
        feature.setType(featureRequestDTO.getFeatureType());
        Feature saved = featureRepository.save(feature);

        return toDTO(saved);
    }

    // Get all features (filtered by type if needed)
    public List<FeatureResponseDTO> getAllFeatures(){
        List<FeatureResponseDTO> features = featureRepository.getAllProjectedFeatures();
        System.out.println(features);
        return  features;
    }

    public FeatureResponseDTO updateFeature(String id, FeatureRequestDTO featureRequestDTO){
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new PolygonNotFoundException("Feature not found with id " + id));

        Geometry geometry = featureRequestDTO.getGeometry();
        if (geometry == null) {
            throw new IllegalArgumentException("Geometry cannot be null");
        }

        feature.setGeometry(geometry);
        feature.setType(featureRequestDTO.getFeatureType());
        Feature updated = featureRepository.save(feature);

        return toDTO(updated);
    }

    public FeatureResponseDTO deleteFeature(String id){
        Feature feature = featureRepository.findById(id)
                .orElseThrow(() -> new PolygonNotFoundException("Feature not found with id " + id));
        feature.setDeleted(true);
        Feature deleted = featureRepository.save(feature);
        return toDTO(deleted);
    }

    public FeatureResponseDTO toDTO(Feature feature){
        FeatureResponseDTO dto = new FeatureResponseDTO();
        dto.setFeatureTypeId(feature.getFeatureTypeId());
        dto.setType(feature.getType());

        Geometry geometry = feature.getGeometry();

        // You can set geometry directly if your DTO supports it
        dto.setGeometry(geometry);

        // If you want to convert GeoJsonPolygon to Map for frontend compatibility, do it here optionally
        // For example, if geometry is Polygon, you could convert to a Map:
        /*
        if (geometry instanceof Polygon polygon) {
            Map<String, Object> geoJsonMap = convertGeoJsonPolygonToMap(polygon);
            // set to dto as needed or return separately
        }
        */

        return dto;
    }

}
