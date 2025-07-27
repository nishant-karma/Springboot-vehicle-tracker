package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.VehicleLocationDTO;
import com.vehicleTracker.vehicleTracker.Repository.LocationUpdateRepository;
import com.vehicleTracker.vehicleTracker.Repository.VehicleRepository;
import com.vehicleTracker.vehicleTracker.loader.RoadPathLoader;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;



@Service
public class VehicleLocationSimulator {

    private final VehicleRepository vehicleRepository;
    private final LocationUpdateRepository locationUpdateRepository;
    private final LocationBroadcastService locationBroadcastService;
    private final RoadPathLoader roadPathLoader;

    private final Map<String, List<List<Double>>> vehiclePaths = new HashMap<>();
    private final Map<String, Integer> pathIndex = new HashMap<>();

    public VehicleLocationSimulator(
            VehicleRepository vehicleRepository,
            LocationUpdateRepository locationUpdateRepository,
            LocationBroadcastService locationBroadcastService,
            RoadPathLoader roadPathLoader
    ) {
        this.vehicleRepository = vehicleRepository;
        this.locationUpdateRepository = locationUpdateRepository;
        this.locationBroadcastService = locationBroadcastService;
        this.roadPathLoader = roadPathLoader;
    }

    @Scheduled(fixedRate = 10000)
    public void updateVehicleLocation() {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        for (Vehicle vehicle : vehicles) {
            String vid = vehicle.getVehicleId();

            // Assign a path if none yet
            if (!vehiclePaths.containsKey(vid)) {
                vehiclePaths.put(vid, roadPathLoader.getRandomPath());
                pathIndex.put(vid, 0);
            }

            List<List<Double>> path = vehiclePaths.get(vid);
            int index = pathIndex.getOrDefault(vid, 0);

            // If we reached the end of the current path, pick a new one and reset index
            if (index >= path.size()) {
                path = roadPathLoader.getRandomPath();
                vehiclePaths.put(vid, path);
                index = 0;
            }

            List<Double> coord = path.get(index);
            double lon = coord.get(0);
            double lat = coord.get(1);

            // Save and broadcast location
            GeoJsonPoint newLocation = new GeoJsonPoint(lon, lat);
            LocationUpdate update = new LocationUpdate(
                    null, vid, newLocation, LocalDateTime.now()
            );
            locationUpdateRepository.save(update);

            VehicleLocationDTO dto = new VehicleLocationDTO();
            dto.setVehicleId(vid);
            dto.setLongitude(lon);
            dto.setLatitude(lat);
            dto.setTimestamp(update.getTimestamp());
            locationBroadcastService.broadcastLocation(dto);

            // Move to next point on path
            pathIndex.put(vid, index + 1);
        }
    }

}



