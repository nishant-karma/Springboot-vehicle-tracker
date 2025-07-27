package com.vehicleTracker.vehicleTracker.service;

import com.vehicleTracker.vehicleTracker.DTO.LiveVehicleDTO;
import com.vehicleTracker.vehicleTracker.DTO.LocationUpdateDTO;
import com.vehicleTracker.vehicleTracker.DTO.LocationUpdateRequest;
import com.vehicleTracker.vehicleTracker.DTO.VehicleLineStringDTO;
import com.vehicleTracker.vehicleTracker.Repository.CoordinatesOnly;
import com.vehicleTracker.vehicleTracker.Repository.LocationUpdateRepository;
import com.vehicleTracker.vehicleTracker.Repository.VehicleRepository;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

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



    public GeoJsonLineString getVehiclePathAsLineString(String vehicleNumber, LocalDateTime from, LocalDateTime to) {
        System.out.println("I am being called");

        List<VehicleLineStringDTO> results = vehicleRepository.getLineStringForVehicle(vehicleNumber, from, to);

        System.out.println("Vehicle: " + vehicleNumber);
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Found results: " + results.size());

        if (results.isEmpty()) {
            System.out.println("❌ No results");
            return null;
        }

        VehicleLineStringDTO dto = results.get(0);

        if (dto.getLineString() == null) {
            System.out.println("🚨 dto.getLineString() is null");
            return null;
        }
        if (dto.getLineString().isEmpty()) {
            System.out.println("⚠️ dto.getLineString() is empty");
            return null;
        }

        System.out.println("✅ Raw coords:");
        dto.getLineString().forEach(c -> System.out.println("  ➤ " + c));

        List<Point> points = dto.getLineString().stream()
                .map(coord -> new Point(coord.get(0), coord.get(1))) // lon, lat
                .toList();

        System.out.println("Points: " + points);

        return new GeoJsonLineString(points);
    }




    public List<LiveVehicleDTO> getAllLiveVehicleData() {
        List<LiveVehicleDTO> list = vehicleRepository.findAllLiveVehiclePositions();
        System.out.println(list);
        return list;
    }








}
