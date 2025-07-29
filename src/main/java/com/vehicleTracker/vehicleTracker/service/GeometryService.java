package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.GeometryDTO;
import com.vehicleTracker.vehicleTracker.DTO.GeometryRequestDTO;
import com.vehicleTracker.vehicleTracker.Repository.GeometryRepository;
import com.vehicleTracker.vehicleTracker.exceptions.PolygonNotFoundException;
import com.vehicleTracker.vehicleTracker.model.Geometry;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeometryService {

    private final GeometryRepository geometryRepository;

    public GeometryService(GeometryRepository geometryRepository){
        this.geometryRepository = geometryRepository;
    }

    public GeometryDTO savePolygon(GeometryRequestDTO geometryRequestDTO){
        System.out.println("I am being called");
        Geometry geometry = new Geometry();
        System.out.println("Coordinates: "+ geometryRequestDTO.getCoordinates());
        geometry.setGeometry(geometryRequestDTO.getCoordinates());
        geometryRepository.save(geometry);
        return toDTO(geometry);
    }


    public List<GeometryDTO> getPolygons() {
        List<Geometry> geometries = geometryRepository.findAll();
        return geometries.stream()
                .map(p -> {
                    GeometryDTO geometryDTO = new GeometryDTO();
                    geometryDTO.setPolygonId(p.getPolygonId());
                    geometryDTO.setGeometry(convertGeoJsonPolygonToMap(p.getGeometry()));
                    return geometryDTO;
                })
                .toList();
    }


    public GeometryDTO updatePolygon(String id, GeometryRequestDTO geometryRequestDTO){
        Geometry geometry = geometryRepository.findById(id).orElseThrow(()-> new PolygonNotFoundException("Polygon not found"));
        geometry.setGeometry(geometryRequestDTO.getCoordinates());
        geometryRepository.save(geometry);

        return toDTO(geometry);
    }

    public GeometryDTO deletePolygon(String id){
        Geometry geometry = geometryRepository.findById(id).orElseThrow(()-> new PolygonNotFoundException("Polygon Not Found"));
        geometry.setDeleted(true);

        geometryRepository.save(geometry);

        return toDTO(geometry);

    }

        public GeometryDTO toDTO(Geometry polygon) {
            GeometryDTO dto = new GeometryDTO();
            dto.setPolygonId(polygon.getPolygonId());

            List<List<List<Double>>> coordinates = new ArrayList<>();

            // For each ring in the polygon (outer ring + holes)
            for (GeoJsonLineString ring : polygon.getGeometry().getCoordinates()) {
                // For each point in ring
                List<List<Double>> ringCoords = ring.getCoordinates()
                        .stream()
                        .map(point -> Arrays.asList(point.getX(), point.getY()))
                        .collect(Collectors.toList());

                coordinates.add(ringCoords);
            }// Wrap once for Polygon

            Map<String, Object> geometry = new HashMap<>();
            geometry.put("type", "Polygon");
            geometry.put("coordinates", coordinates);

            dto.setGeometry(geometry);

            return dto;
        }

    public Map<String, Object> convertGeoJsonPolygonToMap(GeoJsonPolygon polygon) {
        Map<String, Object> geometry = new HashMap<>();
        geometry.put("type", "Polygon");

        // polygon.getCoordinates() returns List<GeoJsonLineString>
        List<List<List<Double>>> coordinates = polygon.getCoordinates()
                .stream()
                .map(lineString -> lineString.getCoordinates()
                        .stream()
                        .map(point -> Arrays.asList(point.getX(), point.getY()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        geometry.put("coordinates", coordinates);
        return geometry;
    }
}
