package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.VehicleLocationDTO;
import com.vehicleTracker.vehicleTracker.Repository.LocationUpdateRepository;
import com.vehicleTracker.vehicleTracker.Repository.VehicleRepository;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class VehicleLocationSimulator {

    private final VehicleRepository vehicleRepository;
    private final LocationUpdateRepository locationUpdateRepository;
    private final LocationBroadcastService locationBroadcastService;

    private final Random random = new Random();

    public VehicleLocationSimulator(VehicleRepository vehicleRepository, LocationUpdateRepository locationUpdateRepository, LocationBroadcastService locationBroadcastService) {
        this.vehicleRepository = vehicleRepository;
        this.locationUpdateRepository = locationUpdateRepository;
        this.locationBroadcastService = locationBroadcastService;
    }

    @Scheduled(fixedRate = 50000)
    public void updateVehicleLocation() {
        List<Vehicle> vehicles = vehicleRepository.findAll();


        for (Vehicle vehicle : vehicles ) {
            // Get last known location or use a default starting point
            LocationUpdate lastLocation = locationUpdateRepository.findTop1ByVehicleIdOrderByTimestampDesc(vehicle.getVehicleId());
            double latitude, longitude;

            if (lastLocation != null) {
                latitude = lastLocation.getLocation().getY();
                longitude = lastLocation.getLocation().getX();
            } else {
                // Default starting point (e.g., Kathmandu)
                latitude = 27.7172;
                longitude = 85.3240;
            }

            // Simulate small movement
            double deltaLat = (random.nextDouble() - 0.5) / 1000;
            double deltaLon = (random.nextDouble() - 0.5) / 1000;

            latitude += deltaLat;
            longitude += deltaLon;

            GeoJsonPoint newLocation = new GeoJsonPoint(longitude, latitude);

            LocationUpdate update = new LocationUpdate(
                    null,
                    vehicle.getVehicleId(),
                    newLocation,
                    LocalDateTime.now()
            );

            locationUpdateRepository.save(update);
            System.out.println("Updated location for vehicle: " + vehicle.getVehicleNumber());

            VehicleLocationDTO dto = new VehicleLocationDTO();
            dto.setVehicleId(vehicle.getVehicleId());
            dto.setLatitude(latitude);
            dto.setLongitude(longitude);
            dto.setTimestamp(update.getTimestamp());

            locationBroadcastService.broadcastLocation(dto);
        }
    }

}

