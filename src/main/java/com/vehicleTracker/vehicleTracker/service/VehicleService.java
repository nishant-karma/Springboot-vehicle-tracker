package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.LocationUpdateDTO;
import com.vehicleTracker.vehicleTracker.DTO.LocationUpdateRequest;
import com.vehicleTracker.vehicleTracker.Repository.LocationUpdateRepository;
import com.vehicleTracker.vehicleTracker.Repository.VehicleRepository;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VehicleService {

    private  final VehicleRepository vehicleRepository;
    private  final LocationUpdateRepository locationUpdateRepository;

    public  VehicleService(VehicleRepository vehicleRepository, LocationUpdateRepository locationUpdateRepository){
        this.vehicleRepository= vehicleRepository;
        this.locationUpdateRepository=locationUpdateRepository;

    }

    public Vehicle createVehicle(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    public LocationUpdate updateLocation(String vehicleId, LocationUpdateRequest req){
        GeoJsonPoint point = new GeoJsonPoint(req.getLatitude(), req.getLongitude());
        LocationUpdate update = new LocationUpdate(null, vehicleId, point, LocalDateTime.now());
        return locationUpdateRepository.save(update);
    }

    public LocationUpdate getLatestLocation(String vehicleId){
        return locationUpdateRepository.findTop1ByVehicleIdOrderByTimestampDesc(vehicleId);
    }

    public List<LocationUpdate> getNearByVehicles(double lon, double lat, double radiusInMeters){
        GeoJsonPoint point = new GeoJsonPoint(lon,lat);
        return locationUpdateRepository.findNearbyVehicles(point, radiusInMeters);

    }






}
