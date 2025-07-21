package com.vehicleTracker.vehicleTracker.controller;


import com.vehicleTracker.vehicleTracker.DTO.LocationUpdateRequest;
import com.vehicleTracker.vehicleTracker.model.LocationUpdate;
import com.vehicleTracker.vehicleTracker.model.Vehicle;
import com.vehicleTracker.vehicleTracker.service.VehicleService;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> create(@RequestBody Vehicle vehicle){
        return ResponseEntity.ok(vehicleService.createVehicle(vehicle));
    }

    @PostMapping("/{id}/location")
    public ResponseEntity<LocationUpdate> updateLocation(@PathVariable String id, LocationUpdateRequest req){
        return ResponseEntity.ok(vehicleService.updateLocation(id,req));
    }

    @GetMapping("/{id}/latest-location")
    public ResponseEntity<LocationUpdate> getLatest(@PathVariable String id){
        return ResponseEntity.ok(vehicleService.getLatestLocation(id));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<LocationUpdate>> getNearBy(@RequestParam double lon, @RequestParam double lat , @RequestParam(defaultValue = "5000") double radius){
        return ResponseEntity.ok(vehicleService.getNearByVehicles(lon,lat,radius));
    }
}

